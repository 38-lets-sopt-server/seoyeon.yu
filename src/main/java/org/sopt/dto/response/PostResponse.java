package org.sopt.dto.response;

import org.sopt.domain.Post;
import org.sopt.domain.User;

public record PostResponse(
        String title,
        String content,
        User user
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getTitle(),
                post.getContent(),
                post.getUser()
        );
    }
}