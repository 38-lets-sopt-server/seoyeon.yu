package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.response.BaseResponse;
import org.sopt.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 API")
@RestController
@RequestMapping("/api/v1")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(summary = "좋아요 추가/취소", description = "좋아요가 없으면 추가, 있으면 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 추가/취소 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PatchMapping("/posts/{postId}/like")
    public ResponseEntity<BaseResponse<Void>> updateLike(
            @Parameter(description = "게시글 id", example = "1", required = true)
            @PathVariable Long postId,

            @Parameter(description = "유저 id", example = "1", required = true)
            @RequestParam Long userId
    ) {
        boolean isLiked = likeService.updateLike(userId, postId);
        String message = isLiked ? "좋아요가 추가되었습니다." : "좋아요가 취소되었습니다.";
        return ResponseEntity.ok(BaseResponse.success(message, null));
    }
}