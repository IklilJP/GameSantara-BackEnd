package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
