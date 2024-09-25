package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.authorize.UserAccount;
import com.example.rakyatgamezomeapi.model.dto.request.UserBioRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserFullNameRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserUsernameRequest;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;
import com.example.rakyatgamezomeapi.model.entity.Post;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.UserRepository;
import com.example.rakyatgamezomeapi.service.ProfilePictureService;
import com.example.rakyatgamezomeapi.service.UserAccountService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.FileUploadUtil;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import com.example.rakyatgamezomeapi.utils.exceptions.UsernameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAccountService userAccountService;
    private final ProfilePictureService profilePictureService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserByToken() {
        UserAccount userAccount = userAccountService.getByContext();
        return toResponse(findByIdOrThrow(userAccount.getId()));
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(String id) {
        return toResponse(findByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByTokenForTsx() {
        UserAccount userAccount = userAccountService.getByContext();
        return findByIdOrNull(userAccount.getId() == null ? "notfound" : userAccount.getId());
    }

    @Override
    public User getUserByEmailForTrx(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByIdForTsx(String id) {
        return findByIdOrThrow(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUserBioByToken(UserBioRequest userRequest) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrow(userAccount.getId());
        user.setBio(userRequest.getBio());
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUserFullNameByToken(UserFullNameRequest userRequest) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrow(userAccount.getId());
        user.setFullName(userRequest.getFullName());
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUserUsernameByToken(UserUsernameRequest userRequest) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrow(userAccount.getId());
        user.setUsername(userRequest.getUsername());
        user.setUpdatedAt(System.currentTimeMillis());
        try {
            return toResponse(userRepository.saveAndFlush(user));
        }catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistException("Username already exists");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUserProfilePicture(MultipartFile profilePicture) {
        UserAccount userAccount = userAccountService.getByContext();
        User user = findByIdOrThrow(userAccount.getId());
        FileUploadUtil.assertAllowedExtension(profilePicture, FileUploadUtil.IMAGE_PATTERN);
        ProfilePicture picture = profilePictureService.upload(profilePicture, user.getId());
        user.setProfilePicture(picture);
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserPassword(String id, String password) {
        User user = findByIdOrThrow(id);
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdatedAt(System.currentTimeMillis());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse banUser(String id) {
        User user = findByIdOrThrow(id);
        user.setIsActive(false);
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse unbanUser(String id) {
        User user = findByIdOrThrow(id);
        user.setIsActive(true);
        user.setUpdatedAt(System.currentTimeMillis());
        return toResponse(userRepository.saveAndFlush(user));
    }

    private User findByIdOrNull(String id) {
        return userRepository.findById(id).orElse(null);
    }

    private User findByIdOrThrow(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .profilePicture(user.getProfilePicture())
                .email(user.getEmail())
                .bio(user.getBio())
                .coin(user.getCoin() == null ? 0 : user.getCoin())
                .followingsCount((long) user.getFollowings().size())
                .followersCount((long) user.getFolloweds().size())
                .upVotesCount(user.getPosts().stream()
                        .flatMap(post -> post.getVotes().stream())
                        .filter(votePost -> votePost.getVoteType().equals(EVoteType.UPVOTE))
                        .count())
                .postsCount((long) user.getPosts().size())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
