package com.example.rakyatgamezomeapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilePictureResponse {
    private String imageUrl;
    private Long createdAt;
    private Long updatedAt;
}
