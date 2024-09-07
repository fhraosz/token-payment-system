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

    public static CardDto of(String cardNumber, String cardExpiry, String cardCvc) {
        return CardDto.builder()
                .cardNumber(cardNumber)
                .cardExpiry(cardExpiry)
                .cardCvc(cardCvc)
                .build();
    }
}
