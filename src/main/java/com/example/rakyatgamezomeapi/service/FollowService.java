package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.response.FollowResponse;

public interface FollowService {
    FollowResponse follow( String userTargetId );
    void unfollow( String userTargetId );
}
