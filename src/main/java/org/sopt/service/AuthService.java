package org.sopt.service;

import lombok.RequiredArgsConstructor;

import org.sopt.domain.RefreshToken;
import org.sopt.domain.User;
import org.sopt.dto.response.TokenResponse;
import org.sopt.repository.RefreshTokenRepository;
import org.sopt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    private User findByCredentials(String email, String password) {
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return user;
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        User user = findByCredentials(email, password);

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(
                RefreshToken.of(user.getId(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }
}
