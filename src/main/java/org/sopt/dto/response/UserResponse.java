package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.User;

public record UserResponse(
        @Schema(description = "사용자 ID")
        Long id,

        @Schema(description = "닉네임")
        String nickname,

        @Schema(description = "이메일")
        String email
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getNickname(), user.getEmail());
    }
}
