package com.example.rakyatgamezomeapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowResponse {
    private String id;
    private String followingUserId;
    private String followedUserId;
    private Long createdAt;
    private Long updatedAt;
}
