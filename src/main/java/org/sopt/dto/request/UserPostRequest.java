package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserPostRequest(
        @Schema(description = "이메일")
        @NotBlank(message = "이메일은 비어있을 수 없습니다.")
        String email,

        @Schema(description = "비밀번호")
        @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
        String password
) {
}