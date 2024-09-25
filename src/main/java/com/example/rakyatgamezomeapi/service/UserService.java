package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.UserBioRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserFullNameRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserUsernameRequest;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;
import com.example.rakyatgamezomeapi.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponse getUserByToken();
    UserResponse getUserById(String id);
    User getUserByTokenForTsx();
    User getUserByEmailForTrx(String email);
    User getUserByIdForTsx(String id);
    UserResponse updateUserBioByToken(UserBioRequest userRequest);
    UserResponse updateUserFullNameByToken(UserFullNameRequest userRequest);
    UserResponse updateUserUsernameByToken(UserUsernameRequest userRequest);
    UserResponse updateUserProfilePicture(MultipartFile profilePicture);
    void updateUserPassword(String id, String password);
    UserResponse banUser(String id);
    UserResponse unbanUser(String id);
}
