package com.example.rakyatgamezomeapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCoinResponse {
    private String id;
    private String name;
    private Long coin;
    private Long price;
    private Long createdAt;
    private Long updatedAt;
}
