package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.ProductCoinRequest;
import com.example.rakyatgamezomeapi.model.dto.response.ProductCoinResponse;
import com.example.rakyatgamezomeapi.model.entity.ProductCoin;

import java.util.List;

public interface ProductCoinService {
    List<ProductCoinResponse> getProductCoins();
    ProductCoinResponse getProductCoinById( String productId );
    ProductCoin getProductCoinByIdForTrx( String productId );
    ProductCoinResponse createProductCoin( ProductCoinRequest productCoinRequest );
    ProductCoinResponse updateProductCoin( ProductCoinRequest productCoinRequest );
    void deleteProductCoin( String productId );
}
