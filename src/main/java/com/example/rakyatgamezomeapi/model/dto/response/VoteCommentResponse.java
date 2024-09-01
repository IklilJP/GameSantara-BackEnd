package com.example.rakyatgamezomeapi.model.dto.response;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteCommentResponse {
    private String id;
    private String commentId;
    private String userId;
    private EVoteType voteType;
    private Long createdAt;
    private Long updatedAt;
}
