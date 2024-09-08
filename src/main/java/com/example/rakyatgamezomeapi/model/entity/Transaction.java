package com.example.rakyatgamezomeapi.model.entity;

import com.example.rakyatgamezomeapi.constant.ETransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne
    @JoinColumn(name = "product_coin_id")
    @JsonIgnore
    private ProductCoin productCoin;

    @Enumerated(EnumType.STRING)
    private ETransactionType type;

    @OneToOne
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;

    private Long createdAt;
    private Long updatedAt;
}
