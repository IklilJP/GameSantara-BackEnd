package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.UserBioRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserFullNameRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserUsernameRequest;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserByToken();
    UserResponse updateUserBioByToken(UserBioRequest userRequest);
    UserResponse updateUserFullNameByToken(UserFullNameRequest userRequest);
    UserResponse updateUserUsernameByToken(UserUsernameRequest userRequest);
}
