package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.ETransactionStatus;
import com.example.rakyatgamezomeapi.constant.ETransactionType;
import com.example.rakyatgamezomeapi.model.dto.request.CommonPaginationRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PaymentRequest;
import com.example.rakyatgamezomeapi.model.dto.request.TransactionRequest;
import com.example.rakyatgamezomeapi.model.dto.response.TransactionResponse;
import com.example.rakyatgamezomeapi.model.entity.Payment;
import com.example.rakyatgamezomeapi.model.entity.ProductCoin;
import com.example.rakyatgamezomeapi.model.entity.Transaction;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.TransactionRepository;
import com.example.rakyatgamezomeapi.service.PaymentService;
import com.example.rakyatgamezomeapi.service.ProductCoinService;
import com.example.rakyatgamezomeapi.service.TransactionService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final PaymentService paymentService;
    private final TransactionRepository transactionRepository;
    private final ProductCoinService productCoinService;
    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionResponse> getTransactionsByUserId(CommonPaginationRequest request) {
        User user = userService.getUserByTokenForTsx();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Transaction> allTrxByUserId = transactionRepository.findByUserId(user.getId(), pageable);
        if(allTrxByUserId.isEmpty()) {
            throw new ResourceNotFoundException("All transaction is empty");
        }
        return allTrxByUserId.map(this::toResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = paymentPurchaseTransaction(id);
        if(transaction.getPayment().getTransactionStatus().equals(ETransactionStatus.SETTLEMENT)){
            User user = transaction.getUser();
            user.setCoin(user.getCoin() + transaction.getProductCoin().getCoin());
            Payment paymentTrx = paymentService.updatePaymentStatusToExpired(transaction.getId());
            transaction.setUser(user);
            transaction.setPayment(paymentTrx);
            return toResponse(transactionRepository.saveAndFlush(transaction));
        }
        return toResponse(transaction);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createPurchaseTransaction(TransactionRequest transactionRequest) {
        User user = userService.getUserByTokenForTsx();
        ProductCoin productCoin = productCoinService.getProductCoinByIdForTrx(transactionRequest.getProductId());
        Transaction transaction = Transaction.builder()
                .user(user)
                .productCoin(productCoin)
                .type(ETransactionType.PURCHASE)
                .createdAt(System.currentTimeMillis())
                .build();
        transaction = transactionRepository.save(transaction);
        Payment payment = paymentService.createPayment(transaction);
        transaction.setPayment(payment);
        return toResponse(transactionRepository.saveAndFlush(transaction));
    }

    private Transaction paymentPurchaseTransaction(String transactionId) {
        Transaction transaction = findByIdOrThrow(transactionId);
        Payment payment = paymentService.checkPayment(transaction.getPayment().getId());
        transaction.setPayment(payment);
        return transactionRepository.saveAndFlush(transaction);
    }

    private Transaction findByIdOrThrow(String id) {
        return transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    private TransactionResponse toResponse( Transaction transaction ) {
        return TransactionResponse.builder()
                .id( transaction.getId() )
                .userId(transaction.getUser().getId())
                .productId(transaction.getProductCoin().getId())
                .totalPrice(transaction.getProductCoin().getPrice())
                .token(transaction.getPayment().getToken())
                .redirectUrl(transaction.getPayment().getRedirectUrl())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
