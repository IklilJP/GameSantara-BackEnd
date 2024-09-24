package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.entity.Payment;
import com.example.rakyatgamezomeapi.model.entity.Transaction;

public interface PaymentService {
    Payment createPayment(Transaction transaction);
    Payment updatePaymentStatusToExpired(String transactionId);
    Payment checkPayment(String orderId);
    void checkFailedAndUpdatePayment();
}
