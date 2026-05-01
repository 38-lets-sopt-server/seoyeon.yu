package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Post", description = "게시글 API")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("게시글이 생성되었습니다.", response));
    }

    // GET /posts
    @Operation(summary = "게시글 전체 조회", description = "게시글 목록을 페이지네이션으로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.")
    })
    @GetMapping
    public ResponseEntity<BaseResponse<Page<PostResponse>>> getAllPosts(
            @Parameter(description = "페이지 번호", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Page<PostResponse> response = postService.getAllPosts(page, size);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // GET /posts/{id}
    @Operation(summary = "게시글 단건 조회", description = "게시글 id로 특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 단건 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 타입의 요청값입니다."),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "게시글 id", example = "1")
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // PUT /posts/{id}
    @Operation(summary = "게시글 수정", description = "게시글 id로 특정 게시글의 제목과 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다."),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @Parameter(description = "게시글 id", example = "1")
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request);
        return ResponseEntity.ok(BaseResponse.success(null));
    }

    // DELETE /posts/{id}
    @Operation(summary = "게시글 삭제", description = "게시글 id로 특정 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 타입의 요청값입니다."),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @Parameter(description = "게시글 id", example = "1")
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.ok(BaseResponse.success("게시글이 삭제되었습니다.", null));
    }
}