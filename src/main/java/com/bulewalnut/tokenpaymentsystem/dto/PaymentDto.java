package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private String refId; // 카드 참조 ID
    private BigDecimal amount; // 결제 금액
    private LocalDateTime requestTime; // 결제 요청 시간
    private String oneTimeToken; // 1회용 토큰

    public static PaymentDto of(PaymentDto paymentDto, String oneTimeToken) {
        return PaymentDto.builder()
                .refId(paymentDto.getRefId())
                .amount(paymentDto.getAmount())
                .requestTime(paymentDto.getRequestTime())
                .oneTimeToken(oneTimeToken)
                .build();
    }
}
