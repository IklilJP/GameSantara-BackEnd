package com.example.rakyatgamezomeapi.service;

public interface PasswordTokenService {
    void sendPasswordResetToken(String email);
    void updatePassword(String token, String newPassword);
}
