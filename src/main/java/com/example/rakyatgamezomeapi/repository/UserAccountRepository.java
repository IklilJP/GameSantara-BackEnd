package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.authorize.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUsername(String username);
}
