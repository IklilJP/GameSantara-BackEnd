package com.example.rakyatgamezomeapi.model.dto.response;

import com.example.rakyatgamezomeapi.constant.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String username;
    private String email;
    private ERole role;
}
