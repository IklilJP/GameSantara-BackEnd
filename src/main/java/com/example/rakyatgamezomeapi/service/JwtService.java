package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.response.JwtClaims;
import com.example.rakyatgamezomeapi.model.authorize.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
