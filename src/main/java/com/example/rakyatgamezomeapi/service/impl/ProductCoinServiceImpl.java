package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.model.dto.request.ProductCoinRequest;
import com.example.rakyatgamezomeapi.model.dto.response.ProductCoinResponse;
import com.example.rakyatgamezomeapi.model.entity.ProductCoin;
import com.example.rakyatgamezomeapi.repository.ProductCoinRepository;
import com.example.rakyatgamezomeapi.service.ProductCoinService;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCoinServiceImpl implements ProductCoinService {
    private final ProductCoinRepository productCoinRepository;
    @Override
    public List<ProductCoinResponse> getProductCoins() {
        List<ProductCoin> productCoins = productCoinRepository.findAll();
        return productCoins.stream().map(this::toResponse).toList();
    }

    @Override
    public ProductCoinResponse getProductCoinById(String productId) {
        return toResponse(findProductCoinByIdOrThrow(productId));
    }

    @Override
    public ProductCoin getProductCoinByIdForTrx(String productId) {
        return findProductCoinByIdOrThrow(productId);
    }

    @Override
    public ProductCoinResponse createProductCoin(ProductCoinRequest productCoinRequest) {
        ProductCoin productCoin = ProductCoin.builder()
                .name(productCoinRequest.getName())
                .coin(productCoinRequest.getCoin())
                .price(productCoinRequest.getPrice())
                .createdAt(System.currentTimeMillis())
                .build();
        return toResponse(productCoinRepository.saveAndFlush(productCoin));
    }

    @Override
    public ProductCoinResponse updateProductCoin(ProductCoinRequest productCoinRequest) {
        ProductCoin productCoin = findProductCoinByIdOrThrow(productCoinRequest.getId());
        productCoin.setName(productCoinRequest.getName());
        productCoin.setCoin(productCoinRequest.getCoin());
        productCoin.setPrice(productCoinRequest.getPrice());
        productCoin.setUpdatedAt(System.currentTimeMillis());
        return toResponse(productCoinRepository.saveAndFlush(productCoin));
    }

    @Override
    public void deleteProductCoin(String productId) {
        productCoinRepository.deleteById(productId);
    }

    private ProductCoin findProductCoinByIdOrThrow(String productId) {
        return productCoinRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private ProductCoinResponse toResponse(ProductCoin productCoin) {
        return ProductCoinResponse.builder()
                .id(productCoin.getId())
                .name(productCoin.getName())
                .coin(productCoin.getCoin())
                .price(productCoin.getPrice())
                .createdAt(productCoin.getCreatedAt())
                .updatedAt(productCoin.getUpdatedAt())
                .build();
    }
}
