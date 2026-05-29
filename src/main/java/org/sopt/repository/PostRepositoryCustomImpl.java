package org.sopt.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.Post;

import java.util.List;

import static org.sopt.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> searchPosts(String title, String nickname) {
        return queryFactory
                .selectFrom(post)
                .join(post.user).fetchJoin()
                .where(
                        titleContains(title),
                        nicknameContains(nickname)
                )
                .fetch();
    }

    private BooleanExpression titleContains(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }
        return post.title.contains(title);
    }

    private BooleanExpression nicknameContains(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return null;
        }
        return post.user.nickname.contains(nickname);
    }
}
