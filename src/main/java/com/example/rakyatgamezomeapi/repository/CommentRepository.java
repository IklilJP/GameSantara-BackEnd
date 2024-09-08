package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {
    Page<Comment> findAllByPostId(String postId, Pageable pageable);
    Optional<Comment> findByIdAndVotesUserIdAndVotesVoteType(String id, String userId, EVoteType voteType);
}
