package com.example.rakyatgamezomeapi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private String id;
    private String content;
    private String username;
    private String profileImageUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentCommentId;

    private String postId;
    private Long upVotesCount;
    private Long downVotesCount;
    private Boolean isUpVoted;
    private Boolean isDownVoted;
    private Long childCommentsCount;

    private Long createdAt;
    private Long updatedAt;
}
