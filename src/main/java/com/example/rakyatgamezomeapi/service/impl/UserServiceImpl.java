package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.ResponseMessage;
import com.example.rakyatgamezomeapi.model.authorize.UserAccount;
import com.example.rakyatgamezomeapi.model.dto.request.UserBioRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserFullNameRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserUsernameRequest;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.UserRepository;
import com.example.rakyatgamezomeapi.service.ProfilePictureService;
import com.example.rakyatgamezomeapi.service.UserAccountService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAccountService userAccountService;
    private final ProfilePictureService profilePictureService;

    @Override
    public UserResponse getUserByToken() {
        UserAccount userAccount = userAccountService.getByContext();
        return toResponse(findByIdOrThrowNotFound(userAccount.getId()));
    }

    @Override
    public UserResponse getUserById(String id) {
        return toResponse(findByIdOrThrowNotFound(id));
    }

    @Override
    public User getUserByTokenForTsx() {
        UserAccount userAccount = userAccountService.getByContext();
        return findByIdOrThrowNotFound(userAccount.getId());
    }

    @Override
    public User getUserByIdForTsx(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public UserResponse updateUserBioByToken(UserBioRequest userRequest) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrowNotFound(userAccount.getId());
        user.setBio(userRequest.getBio());
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponse updateUserFullNameByToken(UserFullNameRequest userRequest) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrowNotFound(userAccount.getId());
        user.setFullName(userRequest.getFullName());
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponse updateUserUsernameByToken(UserUsernameRequest userRequest) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrowNotFound(userAccount.getId());
        user.setUsername(userRequest.getUsername());
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUserProfilePicture(MultipartFile profilePicture) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrowNotFound(userAccount.getId());
        FileUploadUtil.assertAllowedExtension(profilePicture, FileUploadUtil.IMAGE_PATTERN);
        ProfilePicture picture = profilePictureService.upload(profilePicture, user.getId());
        user.setProfilePicture(picture);
        user.setUpdatedAt(System.currentTimeMillis());
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
                .profilePicture(user.getProfilePicture())
                .email(user.getEmail())
                .bio(user.getBio())
                .followingsCount((long) user.getFollowings().size())
                .followersCount((long) user.getFolloweds().size())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
