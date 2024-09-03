package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.request.VotePostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.VotePostResponse;
import com.example.rakyatgamezomeapi.service.VotePostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIUrl.VOTE_POSTS_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class VotePostController {
    private final VotePostService votePostService;

    @PostMapping("{postTargetId}/{vote}")
    public ResponseEntity<CommonResponse<VotePostResponse>> vote(@PathVariable String vote, @PathVariable String postTargetId) {
        VotePostResponse votePostResponse = new VotePostResponse();
        CommonResponse<VotePostResponse> commonResponse = CommonResponse.<VotePostResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Voted successfully")
                .build();
        if(vote.equals("up-vote")){
            votePostResponse = votePostService.vote(postTargetId, EVoteType.UPVOTE);
        }else if(vote.equals("down-vote")){
            votePostResponse = votePostService.vote(postTargetId, EVoteType.DOWNVOTE);
        }

        if(votePostResponse != null) {
            commonResponse.setData(votePostResponse);
        }else{
            commonResponse.setMessage("Unvoted Successfully");
        }

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
