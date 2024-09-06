package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.UserBioRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserFullNameRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserUsernameRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;
import com.example.rakyatgamezomeapi.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(APIUrl.USER_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CommonResponse<UserResponse>> getUserProfile() {
        UserResponse userResponse = userService.getUserByToken();
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User profile retrieved successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserProfileById(@PathVariable("id") String id) {
        UserResponse userResponse = userService.getUserById(id);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User profile retrieved successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping("/bio")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserBio(@Valid @RequestBody UserBioRequest request) {
        UserResponse userResponse = userService.updateUserBioByToken(request);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User profile updated successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping("/fullname")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserFullName(@Valid @RequestBody UserFullNameRequest request) {
        UserResponse userResponse = userService.updateUserFullNameByToken(request);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User profile updated successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping("/username")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserUsername(@Valid @RequestBody UserUsernameRequest request) {
        UserResponse userResponse = userService.updateUserUsernameByToken(request);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User profile updated successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping(value = "/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<UserResponse>> updateProfilePicture(@RequestParam("picture") MultipartFile file) {
        UserResponse userResponse = userService.updateUserProfilePicture(file);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User profile picture updated successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping("/ban-user/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> banUser(@PathVariable String id) {
        UserResponse userResponse = userService.banUser(id);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User has been banned successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping("/unban-user/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> unbanUser(@PathVariable String id) {
        UserResponse userResponse = userService.unbanUser(id);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User has been unbanned successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
