package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.VotePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotePostRepository extends JpaRepository<VotePost, String> {
    Optional<VotePost> findByPostIdAndUserId(String postId, String userId);
}
