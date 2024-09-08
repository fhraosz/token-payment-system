package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private String cardNumber;
    private String cardExpiry;
    private String cardCvc;
    private String refId;
    private String cardNickName;

    public static CardDto of(String cardNumber, String cardExpiry, String cardCvc, String cardNickName) {
        return CardDto.builder()
                .cardNumber(cardNumber)
                .cardExpiry(cardExpiry)
                .cardCvc(cardCvc)
                .cardNickName(cardNickName)
                .build();
    }

    public static CardDto of(String cardNumber, String cardExpiry, String cardCvc, String refId, String cardNickName) {
        return CardDto.builder()
                .cardNumber(cardNumber)
                .cardExpiry(cardExpiry)
                .cardCvc(cardCvc)
                .refId(refId)
                .cardNickName(cardNickName)
                .build();
    }

    public static CardDto of(String refId, String cardNickName) {
        return CardDto.builder()
                .refId(refId)
                .cardNickName(cardNickName)
                .build();
    }
}
