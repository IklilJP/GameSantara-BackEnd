package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
    Optional<ProfilePicture> findByUserId(String userId);
}
