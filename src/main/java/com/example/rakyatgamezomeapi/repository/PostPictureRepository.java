package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostPictureRepository extends JpaRepository<PostPicture, String> {
    Optional<PostPicture> findByPostId(String postId);
}
