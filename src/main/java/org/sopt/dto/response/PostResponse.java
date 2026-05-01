package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.Post;
import org.sopt.domain.User;

import java.time.LocalDateTime;

public record PostResponse(
        @Schema(description = "게시글 제목", example = "제목입니다.")
        String title,

        @Schema(description = "게시글 내용", example = "내용입니다.")
        String content,

        @Schema(description = "게시글 작성자 닉네임", example = "익명")
        String author,

        @Schema(description = "게시글 생성일")
        LocalDateTime createdAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getCreatedAt()
        );
    }
}