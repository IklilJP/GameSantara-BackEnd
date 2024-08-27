package com.example.rakyatgamezomeapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;

    private String password;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "role_id")
    private Role role;

    @OneToOne
    @JoinTable(name="profile_picture_id")
    private ProfilePicture profilePicture;

    private Boolean isActive = true;

    private String bio;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
