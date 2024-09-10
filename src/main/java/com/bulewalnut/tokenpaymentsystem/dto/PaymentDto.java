package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private String refId; // 카드 참조 ID
    private BigDecimal amount; // 결제 금액
    private String token; // 1회용 토큰

    public static PaymentDto setPaymentDto(PaymentDto paymentDto, String token) {
        PaymentDto updatedPaymentDto = new PaymentDto();

        updatedPaymentDto.setRefId(paymentDto.getRefId());
        updatedPaymentDto.setAmount(paymentDto.getAmount());
        updatedPaymentDto.setToken(token);

        return updatedPaymentDto;
    }

}
