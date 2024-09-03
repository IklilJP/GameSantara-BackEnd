package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.request.VoteCommentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.VoteCommentResponse;
import com.example.rakyatgamezomeapi.service.VoteCommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIUrl.VOTE_COMMENTS_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class VoteCommentController {
    private final VoteCommentService voteCommentService;

    @PostMapping("{commentTargetId}/{vote}")
    public ResponseEntity<CommonResponse<VoteCommentResponse>> vote(@PathVariable String commentTargetId, @PathVariable String vote) {
        VoteCommentResponse voteCommentResponse = new VoteCommentResponse();
        CommonResponse<VoteCommentResponse> commonResponse = CommonResponse.<VoteCommentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Voted successfully")
                .build();
        if(vote.equals("up-vote")){
            voteCommentResponse = voteCommentService.vote(commentTargetId, EVoteType.UPVOTE);
        }else if(vote.equals("down-vote")){
            voteCommentResponse = voteCommentService.vote(commentTargetId, EVoteType.DOWNVOTE);
        }

        if(voteCommentResponse != null) {
            commonResponse.setData(voteCommentResponse);
        }else{
            commonResponse.setMessage("Unvoted Successfully");
        }

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}