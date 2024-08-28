package com.example.rakyatgamezomeapi.model.dto.request;

import com.example.rakyatgamezomeapi.constant.ERole;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUsernameRequest {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
}
