package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @Schema(description = "닉네임", example = "김소연")
        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname,

        @Schema(description = "이메일", example = "test@test.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @Schema(description = "비밀번호", example = "password1!")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
