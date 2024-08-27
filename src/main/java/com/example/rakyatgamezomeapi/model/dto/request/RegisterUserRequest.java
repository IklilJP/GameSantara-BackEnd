package com.example.rakyatgamezomeapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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
public class RegisterUserRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20)
    private String password;

    @JsonProperty("full_name")
    @NotBlank(message = "Full Name is required")
    @Size(min = 4, max = 35)
    private String fullName;

    @Email
    private String email;
}
