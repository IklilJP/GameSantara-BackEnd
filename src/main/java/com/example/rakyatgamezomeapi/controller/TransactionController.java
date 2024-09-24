package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.CommonPaginationRequest;
import com.example.rakyatgamezomeapi.model.dto.request.TransactionRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.PagingResponse;
import com.example.rakyatgamezomeapi.model.dto.response.TransactionResponse;
import com.example.rakyatgamezomeapi.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.TRANSACTION_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransactions(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        CommonPaginationRequest request = CommonPaginationRequest.builder()
                .page(Math.max(page-1, 0))
                .size(size)
                .build();

        Page<TransactionResponse> transactionResponses = transactionService.getTransactionsByUserId(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(transactionResponses.getTotalPages())
                .totalElements(transactionResponses.getTotalElements())
                .page(page)
                .size(size)
                .hasPrevious(transactionResponses.hasPrevious())
                .hasNext(transactionResponses.hasNext())
                .build();

        CommonResponse<List<TransactionResponse>> commonResponse = CommonResponse.<List<TransactionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("All transactions retrieved successfully")
                .paging(pagingResponse)
                .data(transactionResponses.getContent())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(@PathVariable("id") String id) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(id);
        CommonResponse<TransactionResponse> commonResponse = CommonResponse.<TransactionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Transaction retrieved successfully")
                .data(transactionResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.createPurchaseTransaction(transactionRequest);
        CommonResponse<TransactionResponse> commonResponse = CommonResponse.<TransactionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Transaction created successfully")
                .data(transactionResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
}
