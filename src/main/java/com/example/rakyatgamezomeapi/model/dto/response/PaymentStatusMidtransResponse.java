package com.example.rakyatgamezomeapi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentStatusMidtransResponse {
    private String transaction_time;
    private String gross_amount;
    private String currency;
    private String order_id;
    private String payment_type;
    private String signature_key;
    private String status_code;
    private String transaction_id;
    private String transaction_status;
    private String fraud_status;
    private String expiry_time;
    private String settlement_time;
    private String status_message;
    private String merchant_id;
    private String transaction_type;
    private String issuer;
    private String acquirer;
}
