package toy.project.apiserver.domain.common.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import toy.project.apiserver.com.provider.JwtTokenProvider;
import toy.project.apiserver.common.exception.AuthenticationException;
import toy.project.apiserver.common.exception.DuplicateResourceException;
import toy.project.apiserver.common.exception.ErrorCode;
import toy.project.apiserver.common.exception.ResourceNotFoundException;
import toy.project.apiserver.domain.common.user.dto.*;
import toy.project.apiserver.domain.common.user.entity.RefreshTokenEntity;
import toy.project.apiserver.domain.common.user.entity.UserEntity;
import toy.project.apiserver.domain.common.user.repository.RefreshTokenRepository;
import toy.project.apiserver.domain.common.user.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    /**
     * 사용자 로그인
     */
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        // Spring Security 인증 (BadCredentialsException은 GlobalExceptionHandler에서 처리)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 사용자 정보 조회
        UserEntity userEntity = userDetailsService.getUserEntityByUsername(loginRequest.getUsername());

        // centerId 업데이트 (필요시)
        if (loginRequest.getCenterId() != null) {
            userEntity.setCenterId(loginRequest.getCenterId());
            userRepository.save(userEntity);
        } else if (userEntity.getCenterId() == null) {
            userEntity.setCenterId(1);
            userRepository.save(userEntity);
        }

        // JWT Access Token 생성
        String accessToken = jwtTokenProvider.createAccessToken(userEntity);
        
        // Refresh Token 생성 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(userEntity);
        saveRefreshToken(userEntity.getUserId(), refreshToken);

        // 응답 DTO 생성
        UserDto userDto = UserDto.from(userEntity);
        LoginResponseDto response = LoginResponseDto.of(accessToken, userDto);
        response.setRefreshToken(refreshToken);
        
        log.info("로그인 성공: username={}", loginRequest.getUsername());
        return response;
    }

    /**
     * 사용자 회원가입
     */
    @Transactional
    public UserDto register(RegisterRequestDto registerRequest) {
        // 중복 체크
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw DuplicateResourceException.duplicateUsername(registerRequest.getUsername());
        }

        if (registerRequest.getEmail() != null && userRepository.existsByEmail(registerRequest.getEmail())) {
            throw DuplicateResourceException.duplicateEmail(registerRequest.getEmail());
        }

        // 사용자 엔티티 생성
        UserEntity userEntity = UserEntity.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .phone(registerRequest.getPhone())
                .role(UserEntity.UserRole.ROLE_USER)
                .enabled(true)
                .centerId(registerRequest.getCenterId() != null ? registerRequest.getCenterId() : 1)
                .build();

        UserEntity savedUser = userRepository.save(userEntity);
        log.info("새로운 사용자 등록 완료: {}", savedUser.getUsername());

        return UserDto.from(savedUser);
    }

    /**
     * JWT 토큰에서 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    public UserDto getUserFromToken(String token) {
        Long userId = jwtTokenProvider.getUserId(token);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
        return UserDto.from(userEntity);
    }

    /**
     * Refresh Token으로 Access Token 재발급
     */
    @Transactional
    public TokenRefreshResponseDto refreshAccessToken(String refreshToken) {
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }

        // DB에서 Refresh Token 조회
        RefreshTokenEntity tokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.INVALID_TOKEN, "유효하지 않은 Refresh Token입니다."));

        // 토큰 유효성 확인
        if (!tokenEntity.isValid()) {
            throw new AuthenticationException(ErrorCode.EXPIRED_TOKEN, "만료되었거나 무효화된 Refresh Token입니다.");
        }

        // 사용자 정보 조회
        UserEntity userEntity = userRepository.findById(tokenEntity.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!userEntity.getEnabled()) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 새로운 Access Token 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(userEntity);
        
        // 새로운 Refresh Token 발급 (선택사항 - Refresh Token Rotation)
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userEntity);
        
        // 기존 Refresh Token 무효화
        tokenEntity.setRevoked(true);
        refreshTokenRepository.save(tokenEntity);
        
        // 새 Refresh Token 저장
        saveRefreshToken(userEntity.getUserId(), newRefreshToken);

        log.info("토큰 갱신 완료: userId={}, username={}", userEntity.getUserId(), userEntity.getUsername());

        return TokenRefreshResponseDto.of(
                newAccessToken,
                newRefreshToken,
                jwtTokenProvider.getAccessTokenExpTime()
        );
    }

    /**
     * Refresh Token 저장
     */
    private void saveRefreshToken(Long userId, String refreshToken) {
        LocalDateTime expiresAt = LocalDateTime.now()
                .plusSeconds(jwtTokenProvider.getRefreshTokenExpTime() / 1000);

        RefreshTokenEntity tokenEntity = RefreshTokenEntity.builder()
                .token(refreshToken)
                .userId(userId)
                .expiresAt(expiresAt)
                .revoked(false)
                .build();

        refreshTokenRepository.save(tokenEntity);
    }

    /**
     * 사용자의 모든 Refresh Token 무효화 (로그아웃 전체)
     */
    @Transactional
    public void revokeAllRefreshTokens(Long userId) {
        refreshTokenRepository.revokeAllByUserId(userId);
        log.info("사용자의 모든 Refresh Token 무효화: userId={}", userId);
    }

    /**
     * 만료된 Refresh Token 정리 (스케줄러에서 호출)
     */
    @Transactional
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
        log.info("만료된 Refresh Token 정리 완료");
    }
}

