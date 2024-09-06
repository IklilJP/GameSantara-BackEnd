package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.CommonPaginationRequest;
import com.example.rakyatgamezomeapi.model.dto.request.CommentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommentResponse;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.PagingResponse;
import com.example.rakyatgamezomeapi.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.COMMENT_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping()
    public ResponseEntity<CommonResponse<List<CommentResponse>>> getAllComments(
            @RequestParam(value = "postId") String postId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        CommonPaginationRequest request = CommonPaginationRequest.builder()
                .page(Math.max(page - 1, 0))
                .size(size)
                .build();
        Page<CommentResponse> commentResponsePage = commentService.getCommentsByPostId(postId, request);
        PagingResponse commentPaging = PagingResponse.builder()
                .totalPages(commentResponsePage.getTotalPages())
                .totalElements(commentResponsePage.getTotalElements())
                .page(page)
                .size(size)
                .hasPrevious(commentResponsePage.hasPrevious())
                .hasNext(commentResponsePage.hasNext())
                .build();

        CommonResponse<List<CommentResponse>> responseCommonResponse = CommonResponse.<List<CommentResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Comments were retrieved successfully")
                .paging(commentPaging)
                .data(commentResponsePage.getContent())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseCommonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CommentResponse>> getComment(@PathVariable String id) {
        CommentResponse commentResponse = commentService.getCommentById(id);
        CommonResponse<CommentResponse> responseCommonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Comment were retrieved successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseCommonResponse);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CommentResponse>> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.addComment(commentRequest);
        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment were created successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/child-comment")
    public ResponseEntity<CommonResponse<CommentResponse>> addChildComment(@Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.addChildComment(commentRequest);
        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment child were created successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CommentResponse>> updateComment(@Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.updateComment(commentRequest);
        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Comment were updated successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Comment deleted successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
