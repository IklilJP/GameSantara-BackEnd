package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.ResponseMessage;
import com.example.rakyatgamezomeapi.model.authorize.UserAccount;
import com.example.rakyatgamezomeapi.model.dto.request.UserRequest;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;
import com.example.rakyatgamezomeapi.model.entity.Role;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.UserRepository;
import com.example.rakyatgamezomeapi.service.RoleService;
import com.example.rakyatgamezomeapi.service.UserAccountService;
import com.example.rakyatgamezomeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAccountService userAccountService;
    private final RoleService roleService;

    @Override
    public UserResponse create(UserRequest userRequest) {
        Role role = roleService.getOrSave(userRequest.getRole());
        User user = User.builder()
                .id(userRequest.getId())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .profilePicture(userRequest.getProfilePicture())
                .role(role)
                .bio(userRequest.getBio())
                .build();
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponse getUserByToken() {
        UserAccount userAccount = userAccountService.getByContext();
        return toResponse(findByIdOrThrowNotFound(userAccount.getId()));
    }

    @Override
    public UserResponse updateUserByToken(UserRequest userRequest) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrowNotFound(userAccount.getId());
        user.setBio(userRequest.getBio());
        return toResponse(userRepository.saveAndFlush(user));
    }

    private User findByIdOrThrowNotFound(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .bio(user.getBio())
                .build();
    }
}
