package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.response.VotePostResponse;

public interface VotePostService {
    VotePostResponse vote(String postTargetId, EVoteType voteType);
}
