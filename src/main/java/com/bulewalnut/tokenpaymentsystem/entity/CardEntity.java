package com.bulewalnut.tokenpaymentsystem.entity;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "card", indexes = {
        @Index(name = "idx_card_1", columnList = "ref_id"),
        @Index(name = "idx_card_2", columnList = "card_nick_name, user_ci")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {

    @Id
    @Column(name = "card_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_expiry", nullable = false)
    private String cardExpiry;

    @Column(name = "card_cvc", nullable = false)
    private String cardCvc;

    @Column(name = "ref_id", nullable = false, unique = true, length = 100)
    private String refId;

    @Column(name = "user_ci", nullable = false, length = 100)
    private String userCi;

    @Column(name = "card_nick_name", nullable = false, length = 20)
    private String cardNickName;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static CardEntity buildCardEntity(CardDto cardDto, String refID, String userCi){
        return CardEntity.builder()
                .cardNumber(cardDto.getCardNumber())
                .cardExpiry(cardDto.getCardExpiry())
                .cardCvc(cardDto.getCardCvc())
                .refId(refID)
                .userCi(userCi)
                .cardNickName(cardDto.getCardNickName())
                .createdAt(cardDto.getCreatedAt())
                .updatedAt(cardDto.getUpdatedAt())
                .build();
    }
}
