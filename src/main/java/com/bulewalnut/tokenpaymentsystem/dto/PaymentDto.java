package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private String refId; // 카드 참조 ID
    private BigDecimal amount; // 결제 금액
    private String token; // 1회용 토큰

    public static PaymentDto of(PaymentDto paymentDto, String token) {
        return PaymentDto.builder()
                .refId(paymentDto.getRefId())
                .amount(paymentDto.getAmount())
                .token(token)
                .build();
    }
}
