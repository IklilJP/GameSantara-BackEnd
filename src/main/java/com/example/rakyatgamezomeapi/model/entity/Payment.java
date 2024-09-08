package com.example.rakyatgamezomeapi.model.entity;

import com.example.rakyatgamezomeapi.constant.ETransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;

    @Column(name = "redirect_url")
    private String redirectUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private ETransactionStatus transactionStatus;

    @OneToOne(mappedBy = "payment")
    private Transaction transaction;
}
