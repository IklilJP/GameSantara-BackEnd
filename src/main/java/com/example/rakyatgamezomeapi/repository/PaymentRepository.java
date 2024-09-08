package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.constant.ETransactionStatus;
import com.example.rakyatgamezomeapi.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findAllByTransactionStatusIn(Collection<ETransactionStatus> transactionStatus);
}
