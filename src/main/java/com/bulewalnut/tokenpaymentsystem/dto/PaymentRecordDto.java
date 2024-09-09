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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("refId")
    private String refId; // 카드 참조값

    @JsonProperty("amount")
    private BigDecimal amount; // 결제 금액

    @JsonProperty("status")
    private String status; // 결제 상태 (예: APPROVED, FAILED)

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    public static PaymentRecordDto of(PaymentRecordEntity entity) {
        return PaymentRecordDto.builder()
                .id(entity.getId())
                .transactionId(entity.getTransactionId())
                .refId(entity.getRefId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
