package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.repository.PasswordTokenRepository;
import com.example.rakyatgamezomeapi.service.PasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordTokenServiceImpl implements PasswordTokenService {
    private final PasswordTokenRepository passwordTokenRepository;
    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetToken(String email) {

    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
