package com.example.rakyatgamezomeapi.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCoinRequest {
    private String id;
    private String name;
    private Long coin;
    private Long price;
    private Long createdAt;
    private Long updatedAt;
}
