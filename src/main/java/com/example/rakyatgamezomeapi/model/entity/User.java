package com.example.rakyatgamezomeapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@EqualsAndHashCode(exclude = {"profilePicture", "role"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    @OneToOne
    @JoinColumn(name="profile_picture_id")
    @JsonIgnore
    private ProfilePicture profilePicture;

    @OneToMany(mappedBy = "followingUser")
    @JsonIgnore
    private List<Follow> followings;

    @OneToMany(mappedBy = "followedUser")
    @JsonIgnore
    private List<Follow> followeds;

    private Boolean isActive = true;

    private String bio;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
