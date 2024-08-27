package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
