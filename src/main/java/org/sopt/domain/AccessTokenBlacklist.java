package org.sopt.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_token_blacklist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private AccessTokenBlacklist(String token, LocalDateTime expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public static AccessTokenBlacklist of(String token, LocalDateTime expiresAt) {
        return new AccessTokenBlacklist(token, expiresAt);
    }
}
