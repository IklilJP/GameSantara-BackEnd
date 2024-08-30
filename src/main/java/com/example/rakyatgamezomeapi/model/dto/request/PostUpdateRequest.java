package com.example.rakyatgamezomeapi.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUpdateRequest {
    private String id;
    private String title;
    private String body;
    private String tagId;
}
