package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.CommonPaginationRequest;
import com.example.rakyatgamezomeapi.model.dto.request.CommentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommentResponse;
import com.example.rakyatgamezomeapi.model.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentResponse> getCommentsByPostId(String postId, CommonPaginationRequest request);
    CommentResponse getCommentById(String id);
    Comment getCommentByIdForTrx(String id);
    CommentResponse addComment(CommentRequest comment);
    CommentResponse addChildComment(CommentRequest comment);
    CommentResponse updateComment(CommentRequest comment);
    void deleteComment(String id);
}
