package com.bulewalnut.tokenpaymentsystem.entity;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardExpiry;

    @Column(nullable = false)
    private String cardCvc;

    @Column(nullable = false)
    private String refId;

    @Column(nullable = false)
    private String userCi;

    @Column(nullable = false)
    private String cardNickName;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    public static CardEntity of(CardDto cardDto, String refID, String userCi){
        return CardEntity.builder()
                .cardNumber(cardDto.getCardNumber())
                .cardExpiry(cardDto.getCardExpiry())
                .cardCvc(cardDto.getCardCvc())
                .refId(refID)
                .userCi(userCi)
                .cardNickName(cardDto.getCardNickName())
                .createdDate(LocalDateTime.now()) // dto에 넣어서 전달하는 방식으로 변경
                .lastModifiedDate(LocalDateTime.now()) // dto에 넣어서 전달하는 방식으로 변경
                .build();
    }
}
