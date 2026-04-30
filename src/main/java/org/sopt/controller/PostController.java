package org.sopt.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("게시글이 생성되었습니다.", response));
    }

    // GET /posts
    @GetMapping
    public ResponseEntity<BaseResponse<Page<PostResponse>>> getAllPosts(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Page<PostResponse> response = postService.getAllPosts(page, size);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // GET /posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // PUT /posts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request);
        return ResponseEntity.ok(BaseResponse.success(null));
    }

    // DELETE /posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.ok(BaseResponse.success("게시글이 삭제되었습니다.", null));
    }
}