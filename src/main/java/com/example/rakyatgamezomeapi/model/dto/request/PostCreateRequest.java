package com.example.rakyatgamezomeapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateRequest {
    private String title;
    private String body;
    @NotBlank
    private String tagId;
}
