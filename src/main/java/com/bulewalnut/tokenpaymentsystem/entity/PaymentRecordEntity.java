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
@Table(name = "payment_recode", indexes = {
        @Index(name = "idx_payment_recode_1", columnList = "transaction_id"),
        @Index(name = "idx_payment_recode_2", columnList = "ref_id")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecordEntity {

    @Id
    @Column(name = "payment_record_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentRecordId;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId; // 결제내역ID

    @Column(name = "ref_id", length = 100)
    private String refId; // 카드 참조값

    @Column(name = "amount", nullable = false)
    private BigDecimal amount; // 결제 금액

    @Column(name = "status", nullable = false, length = 20)
    private String status; // 결제 상태 (예: 결제완료, 결제실패)

    @Column(name = "payment_complete_date", length = 10)
    private String paymentCompleteDate; // 결제 완료일 yyyy-mm-dd

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static PaymentRecordEntity bulidPaymentRecordEntity(PaymentDto paymentDto, String transactionId, String status, LocalDateTime now, String paymentCompleteDate) {
        return PaymentRecordEntity.builder()
                .transactionId(transactionId)
                .refId(paymentDto.getRefId())
                .amount(paymentDto.getAmount())
                .status(status)
                .paymentCompleteDate(paymentCompleteDate)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
