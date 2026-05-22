package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.BaseException;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validator.PostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostValidator postValidator;

    // CREATE
    @Transactional
    public CreatePostResponse createPost(Long userId, CreatePostRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        postValidator.validateTitle(request.title());

        Post post = new Post(request.title(), request.content(), user);
        postRepository.save(post);
        return new CreatePostResponse(post.getId());
    }

    // READ ALL
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable)
                .map(post -> PostResponse.from(post, likeRepository.countByPost(post)));
    }

    // READ ONE
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return PostResponse.from(post, likeRepository.countByPost(post));
    }

    // UPDATE
    @Transactional
    public void updatePost(Long userId, Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (!post.getUser().getId().equals(userId)) {
            throw new BaseException(ErrorCode.FORBIDDEN);
        }

        postValidator.validateTitle(request.title());

        post.update(request.title(), request.content());
    }

    // DELETE
    @Transactional
    public void deletePost(Long userId, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (!post.getUser().getId().equals(userId)) {
            throw new BaseException(ErrorCode.FORBIDDEN);
        }

        postRepository.delete(post);
    }
}