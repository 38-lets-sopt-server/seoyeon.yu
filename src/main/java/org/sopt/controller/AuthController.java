package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.User;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.TokenResponse;
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
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        TokenResponse tokens = authService.login(email, password);

        return ResponseEntity.ok(BaseResponse.success(tokens));
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<User>> me(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }

        Long memberId = Long.parseLong(authentication.getName());
        User member = authService.getUserById(memberId);

        return ResponseEntity.ok(BaseResponse.success(member));
    }
}