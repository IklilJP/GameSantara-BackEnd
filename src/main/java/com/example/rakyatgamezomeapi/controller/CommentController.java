package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.CommentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommentResponse;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.COMMENT_API)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<CommentResponse>>> getAllComments() {
        List<CommentResponse> commentResponseList = commentService.getComments();
        CommonResponse<List<CommentResponse>> responseCommonResponse = CommonResponse.<List<CommentResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Comments were retrieved successfully")
                .data(commentResponseList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseCommonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CommentResponse>> getComment(@PathVariable String id) {
        CommentResponse commentResponse = commentService.getCommentById(id);
        CommonResponse<CommentResponse> responseCommonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Comment retrieved successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseCommonResponse);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CommentResponse>> createComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.addComment(commentRequest);
        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment created successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/child-comment")
    public ResponseEntity<CommonResponse<CommentResponse>> addChildComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.addChildComment(commentRequest);
        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment child created successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CommentResponse>> updateComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.updateComment(commentRequest);
        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment child created successfully")
                .data(commentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
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
