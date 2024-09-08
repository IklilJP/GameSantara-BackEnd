package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.request.VoteCommentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.VoteCommentResponse;
import com.example.rakyatgamezomeapi.model.entity.Comment;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.model.entity.VoteComment;
import com.example.rakyatgamezomeapi.repository.VoteCommentRepository;
import com.example.rakyatgamezomeapi.service.CommentService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.service.VoteCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteCommentServiceImpl implements VoteCommentService {
    private final UserService userService;
    private final CommentService commentService;
    private final VoteCommentRepository voteCommentRepository;
    @Override
    public VoteCommentResponse vote(String commentTargetId, EVoteType voteType) {
        Comment comment = commentService.getCommentByIdForTrx(commentTargetId);
        User user = userService.getUserByTokenForTsx();
        VoteComment voteComment = voteCommentRepository.findByCommentIdAndUserId(commentTargetId, user.getId()).orElse(null);
        if (voteComment == null) {
            voteComment = VoteComment.builder()
                    .comment(comment)
                    .voteType(voteType)
                    .user(user)
                    .createdAt(System.currentTimeMillis())
                    .build();
            return toResponse(voteCommentRepository.saveAndFlush(voteComment));
        }else if(voteComment.getVoteType() == voteType && voteComment.getComment().getId().equals(comment.getId())) {
            voteCommentRepository.delete(voteComment);
            return null;
        }else{
            voteComment.setVoteType(voteType);
            voteComment.setUpdatedAt(System.currentTimeMillis());
            return toResponse(voteCommentRepository.saveAndFlush(voteComment));
        }

    }

    private VoteCommentResponse toResponse(VoteComment voteComment) {
        return VoteCommentResponse.builder()
                .id(voteComment.getId())
                .commentId(voteComment.getComment().getId())
                .userId(voteComment.getUser().getId())
                .voteType(voteComment.getVoteType())
                .createdAt(voteComment.getCreatedAt())
                .updatedAt(voteComment.getUpdatedAt())
                .build();
    }
}
