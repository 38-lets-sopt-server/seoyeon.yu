package org.sopt.controller;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public ApiResponse<CreatePostResponse> createPost(CreatePostRequest request) {
        try {
            CreatePostResponse response = postService.createPost(request);
            return ApiResponse.success("게시글 등록 완료!", response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    // GET /posts
    public ApiResponse<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ApiResponse.success("전체 게시글 조회 성공", posts);
    }

    // GET /posts/{id}
    public ApiResponse<PostResponse> getPost(Long id) {
        try {
            PostResponse post = postService.getPost(id);
            return ApiResponse.success("게시글 조회 성공", post);
        } catch (PostNotFoundException e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    // PUT /posts/{id}
    public ApiResponse<Void> updatePost(Long id, String newTitle, String newContent) {
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success("게시글 수정 완료!");
        } catch (IllegalArgumentException | PostNotFoundException e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    // DELETE /posts/{id}
    public ApiResponse<Void> deletePost(Long id) {
        try {
            postService.deletePost(id);
            return ApiResponse.success("게시글 삭제 완료!");
        } catch (PostNotFoundException e) {
            return ApiResponse.fail(e.getMessage());
        }
    }
}