package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.ERole;
import com.example.rakyatgamezomeapi.model.dto.request.AuthRequest;
import com.example.rakyatgamezomeapi.model.dto.request.RegisterUserRequest;
import com.example.rakyatgamezomeapi.model.dto.response.LoginResponse;
import com.example.rakyatgamezomeapi.model.dto.response.RegisterResponse;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import com.example.rakyatgamezomeapi.model.entity.Role;
import com.example.rakyatgamezomeapi.model.authorize.UserAccount;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.UserRepository;
import com.example.rakyatgamezomeapi.service.AuthService;
import com.example.rakyatgamezomeapi.service.JwtService;
import com.example.rakyatgamezomeapi.service.ProfilePictureService;
import com.example.rakyatgamezomeapi.service.RoleService;
import com.example.rakyatgamezomeapi.utils.exceptions.EmailAlreadyExistsException;
import com.example.rakyatgamezomeapi.utils.exceptions.UsernameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final ProfilePictureService profilePictureService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerUser(RegisterUserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Role role = roleService.getOrSave(ERole.USER);
        User user = User.builder()
                .username(request.getUsername().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .email(request.getEmail())
                .fullName(request.getFullName())
                .coin(0L)
                .createdAt(System.currentTimeMillis())
                .build();
        user = userRepository.save(user);
        ProfilePicture dummyProfilePicture = profilePictureService.createDefaultProfilePicture(user.getId());
        user.setProfilePicture(dummyProfilePicture);
        userRepository.saveAndFlush(user);
        return RegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(RegisterUserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Role role = roleService.getOrSave(ERole.ADMIN);
        User user = User.builder()
                .username(request.getUsername().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .email(request.getEmail())
                .fullName(request.getFullName())
                .createdAt(System.currentTimeMillis())
                .build();
        user = userRepository.save(user);
        ProfilePicture dummyProfilePicture = profilePictureService.createDefaultProfilePicture(user.getId());
        user.setProfilePicture(dummyProfilePicture);
        userRepository.saveAndFlush(user);
        return RegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        Authentication authenticated = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        UserAccount userAccount = (UserAccount) authenticated.getPrincipal();

        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .userId(userAccount.getId())
                .token(token)
                .email(userAccount.getEmail())
                .role(userAccount.getRole().getName())
                .build();
    }

    @Override
    public boolean validateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getPrincipal().toString()).orElse(null);
        return user != null;
    }
}
