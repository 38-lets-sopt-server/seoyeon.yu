package org.sopt.service;

import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.exception.BaseException;
import org.sopt.exception.ErrorCode;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean updateLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));

        if (likeRepository.findByUserAndPost(user, post).isPresent()) {
            likeRepository.findByUserAndPost(user, post)
                    .ifPresent(likeRepository::delete);
            return false;
        } else {
            likeRepository.save(new Like(user, post));
            return true;
        }
    }
}