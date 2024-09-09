package com.bulewalnut.tokenpaymentsystem.entity;

import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_recode")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String refId; // 카드 참조값

    @Column(nullable = false)
    private BigDecimal amount; // 결제 금액

    @Column(nullable = false)
    private String status; // 결제 상태 (예: APPROVED, FAILED)

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static PaymentRecordEntity of(PaymentDto paymentDto, String transactionId, String status) {
        LocalDateTime now = LocalDateTime.now();

        return PaymentRecordEntity.builder()
                .transactionId(transactionId)
                .refId(paymentDto.getRefId())
                .amount(paymentDto.getAmount())
                .status(status)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
