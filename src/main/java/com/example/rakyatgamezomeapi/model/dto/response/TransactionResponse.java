package com.example.rakyatgamezomeapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private String userId;
    private String productId;
    private Long totalPrice;
    private String token;
    private String redirectUrl;
    private Long createdAt;
    private Long updatedAt;
}
