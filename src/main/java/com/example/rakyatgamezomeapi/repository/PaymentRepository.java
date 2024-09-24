package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.constant.ETransactionStatus;
import com.example.rakyatgamezomeapi.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByTransactionId( String transactionId );
    List<Payment> findAllByTransactionStatusIn(Collection<ETransactionStatus> transactionStatus);
}
