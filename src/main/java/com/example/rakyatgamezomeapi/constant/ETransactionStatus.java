package com.example.rakyatgamezomeapi.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ETransactionStatus {
    ORDERED("ordered", "Ordered"),
    PENDING("pending", "Pending"),
    SETTLEMENT("settlement", "Settlement"),
    CANCEL("cancel", "Cancel"),
    DENY("deny", "Deny"),
    EXPIRED("expired", "Expired"),
    FAILURE("failure", "Failure");

    private final String name;
    private final String description;

    ETransactionStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ETransactionStatus getByName(String name) {
        return Arrays.stream(values())
                .filter(eTransactionStatus -> eTransactionStatus.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
