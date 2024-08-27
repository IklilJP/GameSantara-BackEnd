package com.example.rakyatgamezomeapi.model.dto.request;

import com.example.rakyatgamezomeapi.constant.ERole;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private ProfilePicture profilePicture;
    private ERole role;
    private String bio;
}
