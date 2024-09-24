package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.ProductCoinRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.ProductCoinResponse;
import com.example.rakyatgamezomeapi.service.ProductCoinService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.PRODUCT_COIN_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ProductCoinController {
    private final ProductCoinService productCoinService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductCoinResponse>>> getProductCoins() {
        List<ProductCoinResponse> productCoinResponseList = productCoinService.getProductCoins();
        CommonResponse<List<ProductCoinResponse>> commonResponse = CommonResponse.<List<ProductCoinResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("All products retrieved successfully")
                .data(productCoinResponseList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductCoinResponse>> getProductCoinById(@PathVariable String id) {
        ProductCoinResponse productCoinResponse = productCoinService.getProductCoinById(id);
        CommonResponse<ProductCoinResponse> commonResponse = CommonResponse.<ProductCoinResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product coin retrieved successfully")
                .data(productCoinResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ProductCoinResponse>> createProductCoin(@RequestBody ProductCoinRequest productCoinRequest) {
        ProductCoinResponse productCoinResponse = productCoinService.createProductCoin(productCoinRequest);
        CommonResponse<ProductCoinResponse> commonResponse = CommonResponse.<ProductCoinResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Product coin added successfully")
                .data(productCoinResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductCoinResponse>> updateProductCoin(@Valid @RequestBody ProductCoinRequest productCoinRequest) {
        ProductCoinResponse productCoinResponse = productCoinService.updateProductCoin(productCoinRequest);
        CommonResponse<ProductCoinResponse> commonResponse = CommonResponse.<ProductCoinResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product coin updated successfully")
                .data(productCoinResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteProductCoin(@PathVariable String id) {
        productCoinService.deleteProductCoin(id);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Product coin deleted successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
