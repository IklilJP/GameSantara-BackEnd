package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.FollowRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.FollowResponse;
import com.example.rakyatgamezomeapi.service.FollowService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIUrl.FOLLOW_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{userTargetId}")
    public ResponseEntity<CommonResponse<FollowResponse>> follow(@PathVariable String userTargetId) {
        FollowResponse followResponse = followService.follow(userTargetId);
        CommonResponse<FollowResponse> commonResponse = CommonResponse.<FollowResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Follow data has been created")
                .data(followResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping("/{userTargetId}")
    public ResponseEntity<CommonResponse<String>> unfollow(@PathVariable String userTargetId) {
        followService.unfollow(userTargetId);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Follow data has been deleted")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
