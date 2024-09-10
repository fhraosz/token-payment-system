package com.bulewalnut.tokenpaymentsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStateEnum {
    APPROVED("결제완료", "APPROVED"),
    FAILED("결제실패", "FAILED");

    private final String state;
    private final String name;
}
