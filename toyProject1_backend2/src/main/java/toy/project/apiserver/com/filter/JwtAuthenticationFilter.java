package toy.project.apiserver.com.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.project.apiserver.com.provider.JwtTokenProvider;
import toy.project.apiserver.domain.common.user.entity.UserEntity;
import toy.project.apiserver.domain.common.user.repository.UserRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 인증 필터 (Sliding Session 지원)
 * - 토큰 검증
 * - 자동 갱신 (만료 15분 전)
 * - 새 토큰을 응답 헤더에 추가
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String NEW_TOKEN_HEADER = "X-New-Access-Token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // JWT 토큰 추출
            String jwt = extractJwtFromRequest(request);

            // 토큰 검증 및 인증 정보 설정
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                // 토큰에서 사용자 정보 추출
                Long userId = jwtTokenProvider.getUserId(jwt);
                String username = jwtTokenProvider.parseClaims(jwt).get("username", String.class);
                String role = jwtTokenProvider.parseClaims(jwt).get("role", String.class);

                // Spring Security 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(role))
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("JWT 토큰 인증 성공: userId={}, username={}", userId, username);
                
                // 슬라이딩 세션: 토큰 갱신 확인 (만료 15분 전)
                if (jwtTokenProvider.shouldRenewToken(jwt)) {
                    renewAccessToken(jwt, userId, response);
                }
            }
        } catch (Exception e) {
            log.error("JWT 토큰 인증 중 오류 발생: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Access Token 자동 갱신
     */
    private void renewAccessToken(String oldToken, Long userId, HttpServletResponse response) {
        try {
            // 사용자 정보 조회
            UserEntity userEntity = userRepository.findById(userId).orElse(null);
            if (userEntity != null && userEntity.getEnabled()) {
                // 새로운 Access Token 발급
                String newAccessToken = jwtTokenProvider.createAccessToken(userEntity);
                
                // 응답 헤더에 새 토큰 추가
                response.setHeader(NEW_TOKEN_HEADER, newAccessToken);
                
                log.info("Access Token 자동 갱신 완료: userId={}, username={}", userId, userEntity.getUsername());
            }
        } catch (Exception e) {
            log.error("토큰 갱신 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    /**
     * Request Header에서 JWT 토큰 추출
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}

