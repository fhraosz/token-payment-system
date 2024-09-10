package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity;
import com.bulewalnut.tokenpaymentsystem.repository.CardRepository;
import com.bulewalnut.tokenpaymentsystem.repository.TokenRepository;
import com.bulewalnut.tokenpaymentsystem.util.RandomKeyUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final CardRepository cardRepository;
    private final TokenRepository tokenRepository;

    @Value("${test.user_ci}")
    private String userCi;

    @Transactional
    public void registerCard(CardDto cardDto, String refId) {
        cardRepository.save(CardEntity.buildCardEntity(cardDto, refId, userCi));
    }

    @Transactional
    public List<CardEntity> findCardByUserCi(String userCi) {
        List<CardEntity> cardEntityList = cardRepository.findByUserCi(userCi);

        if (cardEntityList.isEmpty()) {
            return null;
        }

        return cardEntityList;
    }

    @Transactional
    public String makeTokenAndSave(String refId) {
        String token = RandomKeyUtil.createTokenByRefId(refId);
        LocalDateTime now = LocalDateTime.now();

        TokenEntity tokenEntity = TokenEntity.buildTokenEntity(token, refId, now);
        tokenRepository.save(tokenEntity);

        return tokenEntity.getToken();
    }

    @Transactional
    public TokenEntity findTokenEntityByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Transactional
    public void changeTokenState(String token) {
        tokenRepository.updateTokenStatusAndTimestamp(token, true);
    }
}
