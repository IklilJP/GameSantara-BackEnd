package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
}