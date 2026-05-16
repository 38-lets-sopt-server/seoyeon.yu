package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.dto.request.UserPostRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.TokenResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @RequestBody @Valid UserPostRequest request
    ) {
        TokenResponse tokens = authService.login(request.email(), request.password());

        return ResponseEntity.ok(BaseResponse.success(tokens));
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }

        Long memberId = Long.parseLong(authentication.getName());
        UserResponse userResponse = UserResponse.from(authService.getUserById(memberId));

        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }
}