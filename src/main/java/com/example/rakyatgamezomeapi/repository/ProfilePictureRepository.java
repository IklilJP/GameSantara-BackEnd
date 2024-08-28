package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
}
