package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.AuthRequest;
import com.example.rakyatgamezomeapi.model.dto.request.RegisterUserRequest;
import com.example.rakyatgamezomeapi.model.dto.response.LoginResponse;
import com.example.rakyatgamezomeapi.model.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerUser(RegisterUserRequest request);
    RegisterResponse registerAdmin(RegisterUserRequest request);
    LoginResponse login(AuthRequest request);

    boolean validateToken();
}
