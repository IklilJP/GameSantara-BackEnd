package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.ETransactionStatus;
import com.example.rakyatgamezomeapi.model.dto.request.PaymentDetailRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PaymentRequest;
import com.example.rakyatgamezomeapi.model.dto.response.PaymentMidtransResponse;
import com.example.rakyatgamezomeapi.model.entity.Payment;
import com.example.rakyatgamezomeapi.model.entity.Transaction;
import com.example.rakyatgamezomeapi.repository.PaymentRepository;
import com.example.rakyatgamezomeapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;
    private final String SECRET_KEY;
    private final String BASE_URL;

    @Autowired
    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            RestTemplate restTemplate,
            @Value("${midtrans.api.key}") String secretKey,
            @Value("${midtrans.api.snap-url}") String baseUrlSnap
    )
    {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
        SECRET_KEY = secretKey;
        BASE_URL = baseUrlSnap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Payment createPayment(Transaction transaction) {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentDetail(PaymentDetailRequest.builder()
                        .orderId(transaction.getId())
                        .amount(transaction.getProductCoin().getPrice())
                        .build())
                .build();

        String key = encodeSecretKey(SECRET_KEY);
        HttpHeaders headers = new HttpHeaders() {{
            set("Authorization", "Basic " + key);
        }};

        ResponseEntity<PaymentMidtransResponse> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(paymentRequest, headers),
                new ParameterizedTypeReference<>() {}
        );

        PaymentMidtransResponse body = response.getBody();

        if (body == null) return null;

        Payment payment = Payment.builder()
                .token(body.getToken())
                .redirectUrl(body.getRedirect_url())
                .transactionStatus(ETransactionStatus.ORDERED)
                .build();

        return paymentRepository.saveAndFlush(payment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkFailedAndUpdatePayment() {
        List<ETransactionStatus> transactionStatuses = List.of(
                ETransactionStatus.DENY,
                ETransactionStatus.CANCEL,
                ETransactionStatus.EXPIRED,
                ETransactionStatus.FAILURE
        );

        List<Payment> payments = paymentRepository.findAllByTransactionStatusIn(transactionStatuses);

        payments.forEach(payment -> payment.setTransactionStatus(ETransactionStatus.FAILURE));
    }

    private String encodeSecretKey(String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
