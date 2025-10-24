package toy.project.apiserver.domain.common.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import toy.project.apiserver.common.dto.ApiResponse;
import toy.project.apiserver.common.util.ResponseUtil;
import toy.project.apiserver.domain.common.user.dto.*;
import toy.project.apiserver.domain.common.user.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 로그인 처리 및 JWT 토큰 발급")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        log.info("로그인 시도: username={}", loginRequest.getUsername());
        LoginResponseDto response = authService.login(loginRequest);
        return ResponseUtil.success(response, "로그인에 성공했습니다.");
    }

    /**
     * 회원가입
     */
    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "새로운 사용자 등록")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        log.info("회원가입 시도: username={}", registerRequest.getUsername());
        UserDto userDto = authService.register(registerRequest);
        return ResponseUtil.created(userDto, "회원가입이 완료되었습니다.");
    }

    /**
     * 현재 로그인한 사용자 정보 조회
     */
    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자 정보 조회")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(@RequestHeader("Authorization") String token) {
        // Bearer 제거
        String jwt = token.replace("Bearer ", "");
        UserDto userDto = authService.getUserFromToken(jwt);
        return ResponseUtil.success(userDto);
    }

    /**
     * 로그아웃 (클라이언트에서 토큰 삭제)
     */
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 (클라이언트에서 토큰 삭제 필요)")
    public ResponseEntity<ApiResponse<Void>> logout() {
        SecurityContextHolder.clearContext();
        return ResponseUtil.successMessage("로그아웃 되었습니다.");
    }

    /**
     * 토큰 검증
     */
    @GetMapping("/validate")
    @Operation(summary = "토큰 검증", description = "JWT 토큰 유효성 검증")
    public ResponseEntity<ApiResponse<UserDto>> validateToken(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UserDto userDto = authService.getUserFromToken(jwt);
        return ResponseUtil.success(userDto, "유효한 토큰입니다.");
    }

    /**
     * Refresh Token으로 Access Token 재발급
     */
    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신", description = "Refresh Token을 사용하여 새로운 Access Token 발급")
    public ResponseEntity<ApiResponse<TokenRefreshResponseDto>> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDto request) {
        log.info("토큰 갱신 요청");
        TokenRefreshResponseDto response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseUtil.success(response, "토큰이 갱신되었습니다.");
    }
}

