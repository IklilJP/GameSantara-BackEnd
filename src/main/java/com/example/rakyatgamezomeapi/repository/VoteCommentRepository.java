package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.VoteComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteCommentRepository extends JpaRepository<VoteComment, String> {
    Optional<VoteComment> findByCommentIdAndUserId(String commentId, String userId);
}
