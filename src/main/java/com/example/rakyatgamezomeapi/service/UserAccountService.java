package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.authorize.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    UserAccount loadUserById(String id);
    UserAccount getByContext();
}
