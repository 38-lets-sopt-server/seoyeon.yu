package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;

import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final PostValidator postValidator = new PostValidator();

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.validateTitle(request.title);
        postValidator.validateContent(request.content);

        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(postRepository.generateId(), request.title, request.content, request.author, createdAt);
        postRepository.save(post);
        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getAuthor(), post.getCreatedAt()))
                .toList();
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getAuthor(), post.getCreatedAt());
    }

    // UPDATE 📝 과제
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }

        postValidator.validateTitle(newTitle);
        postValidator.validateContent(newContent);

        post.update(newTitle, newContent);
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);
        if (!deleted) {
            throw new PostNotFoundException(id);
        }
    }
}