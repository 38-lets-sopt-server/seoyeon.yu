package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.dto.request.ReissueTokenRequest;
import org.sopt.dto.request.SignUpRequest;
import org.sopt.dto.request.UserPostRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.TokenResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.exception.BaseException;
import org.sopt.exception.ErrorCode;
import org.sopt.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<UserResponse>> signUp(
            @RequestBody @Valid SignUpRequest request
    ) {
        UserResponse userResponse = authService.signUp(request.nickname(), request.email(), request.password());
        return ResponseEntity.status(201).body(BaseResponse.success("회원가입이 완료되었습니다.", userResponse));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @RequestBody @Valid UserPostRequest request
    ) {
        TokenResponse tokens = authService.login(request.email(), request.password());

        return ResponseEntity.ok(BaseResponse.success("성공적으로 로그인되었습니다.", tokens));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TokenResponse>> reissue(
            @RequestBody @Valid ReissueTokenRequest request
    ) {
        TokenResponse tokens = authService.reissue(request.refreshToken());
        return ResponseEntity.ok(BaseResponse.success(tokens));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            Authentication authentication,
            HttpServletRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();
        String token = extractBearerToken(request);
        authService.logout(userId, token);
        return ResponseEntity.ok(BaseResponse.success("성공적으로 로그아웃되었습니다.", null));
    }

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            throw new BaseException(ErrorCode.AUTH_UNAUTHORIZED);
        }
        return header.substring("Bearer ".length()).trim();
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "내 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        UserResponse userResponse = UserResponse.from(authService.getUserById(userId));

        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }
}