package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.model.dto.request.FollowRequest;
import com.example.rakyatgamezomeapi.model.dto.response.FollowResponse;
import com.example.rakyatgamezomeapi.model.entity.Follow;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.FollowRepository;
import com.example.rakyatgamezomeapi.service.FollowService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FollowResponse follow(String userTargetId) {
        User userAuth = userService.getUserByTokenForTsx();
        User userTarget = userService.getUserByIdForTsx(userTargetId);

        Follow follow = Follow.builder()
                .followingUser(userTarget)
                .followedUser(userAuth)
                .createdAt(System.currentTimeMillis())
                .build();
        return toFollowResponse(followRepository.saveAndFlush(follow));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unfollow(String userTargetId) {
        User userAuth = userService.getUserByTokenForTsx();
        Follow followingTarget = followRepository.findByFollowingUserIdAndFollowedUserId(userTargetId, userAuth.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Following user not found"));
        followRepository.delete(followingTarget);
    }

    private FollowResponse toFollowResponse(Follow follow) {
        return FollowResponse.builder()
                .id(follow.getId())
                .followedUserId(follow.getFollowedUser().getId())
                .followingUserId(follow.getFollowingUser().getId())
                .createdAt(follow.getCreatedAt())
                .updatedAt(System.currentTimeMillis())
                .build();
    }


}
