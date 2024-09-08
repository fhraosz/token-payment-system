package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import com.bulewalnut.tokenpaymentsystem.service.TokenService;
import com.bulewalnut.tokenpaymentsystem.util.RefIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenApplication {

    private final TokenService tokenService;

    public String createRefIdAndRegisterCard(CardDto cardDto) {
        String refId = RefIdUtil.createRefId();
        tokenService.registerCard(cardDto, refId);
        return refId;
    }

    public List<CardDto> findCardByUserCi(String userCi) {
        List<CardEntity> cardEntities = tokenService.findCardByUserCi(userCi);

        if (cardEntities == null) {
            return null;
        }

        List<CardDto> cardDtos = new ArrayList<>();

        cardEntities.forEach
                (cardEntity -> cardDtos.add(CardDto.of(cardEntity.getRefId(), cardEntity.getCardNickName())));

        return cardDtos;
    }
}


