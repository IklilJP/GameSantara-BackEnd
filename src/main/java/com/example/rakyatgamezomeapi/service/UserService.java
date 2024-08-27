package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount loadUserById(String id);
    UserAccount getByContext();
}
