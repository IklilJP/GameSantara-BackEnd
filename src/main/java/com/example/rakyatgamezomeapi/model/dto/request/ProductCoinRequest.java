package com.example.rakyatgamezomeapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCoinRequest {
    @NotBlank
    private String id;

    private String name;
    private Long coin;
    private Long price;
}
