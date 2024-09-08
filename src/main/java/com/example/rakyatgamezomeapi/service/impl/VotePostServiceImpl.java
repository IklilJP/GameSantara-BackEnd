package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.request.VotePostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.VotePostResponse;
import com.example.rakyatgamezomeapi.model.entity.Post;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.model.entity.VotePost;
import com.example.rakyatgamezomeapi.repository.PostRepository;
import com.example.rakyatgamezomeapi.repository.VotePostRepository;
import com.example.rakyatgamezomeapi.service.PostService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.service.VotePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VotePostServiceImpl implements VotePostService {
    private final UserService userService;
    private final PostService postService;
    private final VotePostRepository votePostRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public VotePostResponse vote(String postTargetId, EVoteType voteType) {
        Post post = postService.getPostByIdForTrx(postTargetId);
        User user = userService.getUserByTokenForTsx();
        VotePost votePost = votePostRepository.findByPostIdAndUserId(postTargetId, user.getId()).orElse(null);
        if (votePost == null) {
            votePost = VotePost.builder()
                    .post(post)
                    .voteType(voteType)
                    .user(user)
                    .createdAt(System.currentTimeMillis())
                    .build();
            return toResponse(votePostRepository.saveAndFlush(votePost));
        }else if(votePost.getVoteType() == voteType && votePost.getUser().getId().equals(user.getId())) {
            votePostRepository.delete(votePost);
            return null;
        }else{
            votePost.setVoteType(voteType);
            votePost.setUpdatedAt(System.currentTimeMillis());
            return toResponse(votePostRepository.saveAndFlush(votePost));
        }
    }

    private VotePostResponse toResponse(VotePost votePost) {
        return VotePostResponse.builder()
                .id(votePost.getId())
                .postId(votePost.getPost().getId())
                .userId(votePost.getUser().getId())
                .voteType(votePost.getVoteType())
                .createdAt(votePost.getCreatedAt())
                .updatedAt(votePost.getUpdatedAt())
                .build();
    }
}
