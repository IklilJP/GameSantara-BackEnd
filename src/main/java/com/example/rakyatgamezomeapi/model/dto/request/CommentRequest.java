package com.example.rakyatgamezomeapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private String id;
    private String content;
    private String username;
    private String parentCommentId;

    @NotBlank
    private String postId;
}
