package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.UserRequest;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;

public interface UserService {
    UserResponse create(UserRequest userRequest);
    UserResponse getUserByToken();
    UserResponse updateUserByToken(UserRequest userRequest);
}
