package com.bulewalnut.tokenpaymentsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStateEnum {
    APPROVED("APPROVED", "결제완료"),
    FAILED("FAILED", "결제실패");

    private final String state;
    private final String name;
}
