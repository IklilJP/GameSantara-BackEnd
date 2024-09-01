package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.response.VoteCommentResponse;

public interface VoteCommentService {
    VoteCommentResponse vote(String commentTargetId, EVoteType voteType);
}
