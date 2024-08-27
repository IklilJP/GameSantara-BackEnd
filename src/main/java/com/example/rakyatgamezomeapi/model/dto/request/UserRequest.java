package com.example.rakyatgamezomeapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Username is required")
    private String username;
    private String password;

    @JsonProperty("full_name")
    private String fullName;
    private String email;
    private String roleId;
    private String bio;
}
