package com.example.rakyatgamezomeapi.model.dto.response;

import com.example.rakyatgamezomeapi.model.entity.PostPicture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPictureResponse {
    private String imageUrl;
    private Long createdAt;
    private Long updatedAt;

    public static PostPictureResponse of(PostPicture picture) {
        return PostPictureResponse.builder()
                .imageUrl(picture.getImageUrl())
                .createdAt(picture.getCreatedAt())
                .updatedAt(picture.getUpdatedAt())
                .build();
    }
}
