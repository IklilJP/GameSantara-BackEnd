package com.example.rakyatgamezomeapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "profile_pictures")
public class ProfilePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String image;

    @Column(name = "create_at")
    private Long createdAt;

    @Column(name = "update_at")
    private Long updatedAt;

    @OneToOne(mappedBy = "profilePicture")
    @JsonIgnore
    private User user;
}
