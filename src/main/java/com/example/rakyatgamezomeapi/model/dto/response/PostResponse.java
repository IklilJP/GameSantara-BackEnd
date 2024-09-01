package com.example.rakyatgamezomeapi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String id;
    private String title;
    private String body;
    private String user;
    private String tagName;
    private String tagImgUrl;
    private Long upVotesCount;
    private Long downVotesCount;
    private Long commentsCount;
    private Long createAt;
    private Long updateAt;
}
