package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @Schema(description = "작성자 id", example = "1")
        @NotNull(message = "작성자 id는 필수입니다.")
        Long userId,

        @Schema(description = "게시글 제목", example = "제목입니다.")
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다.")
        String title,

        @Schema(description = "게시글 내용", example = "내용입니다.")
        String content
) {
}