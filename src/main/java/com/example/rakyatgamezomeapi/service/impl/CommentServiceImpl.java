package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.ERole;
import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.request.CommonPaginationRequest;
import com.example.rakyatgamezomeapi.model.dto.request.CommentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommentResponse;
import com.example.rakyatgamezomeapi.model.entity.Comment;
import com.example.rakyatgamezomeapi.model.entity.Post;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.CommentRepository;
import com.example.rakyatgamezomeapi.service.CommentService;
import com.example.rakyatgamezomeapi.service.PostService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.exceptions.AuthenticationException;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getCommentsByPostId(String postId, CommonPaginationRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);
        return comments.map(this::toResponse);
    }

    @Override
    public CommentResponse getCommentById(String id) {
        return toResponse(findCommentByIdOrThrow(id));
    }

    @Override
    public Comment getCommentByIdForTrx(String id) {
        return findCommentByIdOrThrow(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentResponse addComment(CommentRequest request) {
        User user = userService.getUserByTokenForTsx();
        Post post = postService.getPostByIdForTrx(request.getPostId());

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .createdAt(System.currentTimeMillis())
                .build();
        return toResponse(commentRepository.saveAndFlush(comment));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentResponse addChildComment(CommentRequest request) {
        User user = userService.getUserByTokenForTsx();
        Post post = postService.getPostByIdForTrx(request.getPostId());
        Comment parentComment = findCommentByIdOrThrow(request.getParentCommentId());

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .parentComment(parentComment)
                .content(request.getContent())
                .createdAt(System.currentTimeMillis())
                .build();
        return toResponse(commentRepository.saveAndFlush(comment));
    }

    @Override
    public CommentResponse updateComment(CommentRequest request) {
        User user = userService.getUserByTokenForTsx();
        Comment comment = findCommentByIdOrThrow(request.getId());

        if(!comment.getUser().getId().equals(user.getId()) || !(user.getRole().getName().equals(ERole.ADMIN))) {
            throw new AuthenticationException("You don't have permission to update this comment");
        }

        comment.setContent(request.getContent());
        comment.setUpdatedAt(System.currentTimeMillis());
        return toResponse(commentRepository.saveAndFlush(comment));
    }

    @Override
    public void deleteComment(String id) {
        User user = userService.getUserByTokenForTsx();
        Comment comment = findCommentByIdOrThrow(id);

        if(!comment.getUser().getId().equals(user.getId()) || !(user.getRole().getName().equals(ERole.ADMIN))) {
            throw new AuthenticationException("You don't have permission to delete this comment");
        }

        commentRepository.delete(comment);
    }

    private Comment findCommentByIdOrThrow(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }

    private Boolean isUpvoted(String id) {
        User user = userService.getUserByTokenForTsx();
        return commentRepository.findByIdAndVotesUserIdAndVotesVoteType(id, user != null?user.getId() : "notfound", EVoteType.UPVOTE).orElse(null) != null;
    }

    private Boolean isDownvoted(String id) {
        User user = userService.getUserByTokenForTsx();
        return commentRepository.findByIdAndVotesUserIdAndVotesVoteType(id, user != null?user.getId() : "notfound", EVoteType.DOWNVOTE).orElse(null) != null;
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .username(comment.getUser().getUsername())
                .profileImageUrl(comment.getUser().getProfilePicture().getImage())
                .upVotesCount(comment.getVotes()!=null? comment.getVotes().stream().filter(vote -> vote.getVoteType().equals(EVoteType.UPVOTE)).count():0)
                .downVotesCount(comment.getVotes()!=null? comment.getVotes().stream().filter(vote -> vote.getVoteType().equals(EVoteType.DOWNVOTE)).count():0)
                .isUpVoted(isUpvoted(comment.getId()))
                .isDownVoted(isDownvoted(comment.getId()))
                .childCommentsCount((long) (comment.getChildComments()!= null? comment.getChildComments().size(): 0))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .build();
    }
}
