package com.bulewalnut.tokenpaymentsystem.dto;

import com.bulewalnut.tokenpaymentsystem.entity.PaymentRecordEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecordDto {

    @JsonProperty("paymentRecordId")
    private Long paymentRecordId;

    @JsonProperty("transactionId")
    private String transactionId; // 결제내역ID

    @JsonProperty("refId")
    private String refId; // 카드 참조값

    @JsonProperty("amount")
    private BigDecimal amount; // 결제 금액

    @JsonProperty("status")
    private String status; // 결제 상태 (예: 결제완료, 결제실패)

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    public static PaymentRecordDto of(PaymentRecordEntity entity) {
        return PaymentRecordDto.builder()
                .paymentRecordId(entity.getPaymentRecordId())
                .transactionId(entity.getTransactionId())
                .refId(entity.getRefId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
