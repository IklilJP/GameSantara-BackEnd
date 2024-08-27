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
public class LoginResponse {
    private String userId;
    private String email;
    private String token;
    private ERole role;
}
