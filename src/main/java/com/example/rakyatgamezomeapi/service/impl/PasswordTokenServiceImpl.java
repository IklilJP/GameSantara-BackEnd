package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.model.entity.PasswordResetToken;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.PasswordTokenRepository;
import com.example.rakyatgamezomeapi.service.PasswordTokenService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordTokenServiceImpl implements PasswordTokenService {
    private final UserService userService;
    private final PasswordTokenRepository passwordTokenRepository;
    private final JavaMailSender mailSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendPasswordResetToken(String email) {
        User userEmail = userService.getUserByEmailForTrx(email);
        String link = generateToken(userEmail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Token");
        message.setText("Click on the link to reset your password: " + link);
        mailSender.send(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String token, String newPassword) {
        PasswordResetToken passwordToken = passwordTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if(passwordToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResourceNotFoundException("Expired password token");
        }

        User user = passwordToken.getUser();
        userService.updateUserPassword(user.getId(), newPassword);

        //After reset password, set expire at now
        passwordToken.setExpiresAt(LocalDateTime.now());
        passwordTokenRepository.saveAndFlush(passwordToken);
    }

    private String generateToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusMinutes(30);
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(uuid.toString())
                .user(user)
                .expiresAt(expiryTime)
                .createdAt(System.currentTimeMillis())
                .build();
        resetToken = passwordTokenRepository.save(resetToken);
        return "http://localhost:8080/reset-password?token=" + resetToken.getToken();
    }
}
