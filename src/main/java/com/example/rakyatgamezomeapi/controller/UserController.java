package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.constant.ResponseMessage;
import com.example.rakyatgamezomeapi.model.dto.request.UserBioRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserFullNameRequest;
import com.example.rakyatgamezomeapi.model.dto.request.UserUsernameRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;
import com.example.rakyatgamezomeapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIUrl.USER_API)
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
}
