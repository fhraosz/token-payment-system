package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity;
import com.bulewalnut.tokenpaymentsystem.repository.CardRepository;
import com.bulewalnut.tokenpaymentsystem.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final CardRepository cardRepository;
    private final TokenRepository tokenRepository;

    @Transactional
    public void registerCard(CardDto cardDto, String refId) {
        cardRepository.save(CardEntity.of(cardDto, refId, "test123"));
    }

    @Transactional
    public List<CardEntity> findCardByUserCi(String userCi) {
        List<CardEntity> cardEntityList = cardRepository.findByUserCi(userCi);

        if (cardEntityList.isEmpty()) {
            return null;
        }

        return cardEntityList;
    }

    public String makeTokenAndSave(String refId) {
        TokenEntity tokenEntity = TokenEntity.of(refId);
        tokenRepository.save(tokenEntity);

        return tokenEntity.getToken();
    }
}
