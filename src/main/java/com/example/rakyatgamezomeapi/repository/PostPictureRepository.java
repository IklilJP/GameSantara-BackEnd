package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostPictureRepository extends JpaRepository<PostPicture, String> {
    List<PostPicture> findAllByPostId(String postId);
}
