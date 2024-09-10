package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private String cardNumber; // 카드번호
    private String cardExpiry; // 카드유효일자
    private String cardCvc; // cvc
    private String refId; // 카드 redId
    private String cardNickName; // 카드별명
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CardDto setCardDto(String cardNumber, String cardExpiry, String cardCvc, String cardNickName) {
        CardDto cardDto = new CardDto();

        cardDto.setCardNumber(cardNumber);
        cardDto.setCardExpiry(cardExpiry);
        cardDto.setCardCvc(cardCvc);
        cardDto.setCardNickName(cardNickName);
        cardDto.setCreatedAt(LocalDateTime.now());
        cardDto.setUpdatedAt(LocalDateTime.now());

        return cardDto;
    }

    public static CardDto setCardDto(String refId, String cardNickName) {
        CardDto cardDto = new CardDto();

        cardDto.setRefId(refId);
        cardDto.setCardNickName(cardNickName);

        return cardDto;
    }
}
