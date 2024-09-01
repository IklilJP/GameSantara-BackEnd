package com.example.rakyatgamezomeapi.model.dto.response;

import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private ProfilePicture profilePicture;
    private String bio;
    private Long followersCount;
    private Long followingsCount;
    private Long createdAt;
    private Long updatedAt;
}
