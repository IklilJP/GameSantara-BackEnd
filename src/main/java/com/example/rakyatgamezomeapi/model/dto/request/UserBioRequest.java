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
public class UserBioRequest {
    @Size(max=100)
    private String bio;
}
