package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.CommentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommentResponse;
import com.example.rakyatgamezomeapi.model.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getComments();
    CommentResponse getCommentById(String id);
    Comment getCommentByIdForTrx(String id);
    CommentResponse addComment(CommentRequest comment);
    CommentResponse addChildComment(CommentRequest comment);
    CommentResponse updateComment(CommentRequest comment);
    void deleteComment(String id);
}
