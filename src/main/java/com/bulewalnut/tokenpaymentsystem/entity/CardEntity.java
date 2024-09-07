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
@Setter
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
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }
}
