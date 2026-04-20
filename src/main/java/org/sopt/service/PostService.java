package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;

    public PostService(PostRepository postRepository, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.postValidator = postValidator;
    }

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.validateTitle(request.title());

        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(
                postRepository.generateId(),
                request.title(),
                request.content(),
                request.author(),
                createdAt
        );

        postRepository.save(post);
        return new CreatePostResponse(post.getId());
    }
}