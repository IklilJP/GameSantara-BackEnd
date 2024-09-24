package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.CommonPaginationRequest;
import com.example.rakyatgamezomeapi.model.dto.request.TransactionRequest;
import com.example.rakyatgamezomeapi.model.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    Page<TransactionResponse> getTransactionsByUserId(CommonPaginationRequest request);
    TransactionResponse getTransactionById(String transactionId);
    TransactionResponse createPurchaseTransaction( TransactionRequest transactionRequest );
}
