package org.sopt.service;


import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validator.PostValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostValidator postValidator;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postValidator = postValidator;
    }

    // CREATE
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.validateTitle(request.title());

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException());

        Post post = new Post(request.title(), request.content(), user);
        postRepository.save(post);
        return new CreatePostResponse(post.getId());
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(int page, int size) {
        List<Post> allPosts = postRepository.findAll();

        return allPosts.stream()
                .skip((long) page * size)
                .limit(size)
                .map(PostResponse::from)
                .toList();
    }

    // READ ONE
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return PostResponse.from(post);
    }

    // UPDATE
    @Transactional
    public void updatePost(Long id, UpdatePostRequest request) {
        postValidator.validateTitle(request.title());

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        post.update(request.title(), request.content());
    }

    // DELETE
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        postRepository.delete(post);
    }
}