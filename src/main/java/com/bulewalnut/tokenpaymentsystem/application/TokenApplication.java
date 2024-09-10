package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity;
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.bulewalnut.tokenpaymentsystem.exception.ValidateException;
import com.bulewalnut.tokenpaymentsystem.service.TokenService;
import com.bulewalnut.tokenpaymentsystem.util.RandomKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenApplication {

    private final TokenService tokenService;

    public String createRefIdAndRegisterCard(CardDto cardDto) {
        try {
            String refId = RandomKeyUtil.createRefId();
            tokenService.registerCard(cardDto, refId);
            return refId;
        } catch (RestClientException e) {
            log.error("카드 등록에 실패하였습니다. ", e);
            throw new RestClientException("카드 등록에 실패하였습니다.");
        }
    }

    public List<CardDto> findCardByUserCi(String userCi) {
        List<CardEntity> cardEntities = tokenService.findCardByUserCi(userCi);

        if (cardEntities == null) {
            return null;
        }

        List<CardDto> cardDtos = new ArrayList<>();

        cardEntities.forEach(cardEntity -> cardDtos.add(CardDto.setCardDto(cardEntity.getRefId(), cardEntity.getCardNickName())));

        return cardDtos;
    }

    public String getTokenByRefId(String refId) {
        return tokenService.makeTokenAndSave(refId);
    }

    public Boolean findTokenEntityAndValidateToken(String token) {
        TokenEntity tokenEntity = tokenService.findTokenEntityByToken(token);
        return validateToken(tokenEntity);
    }

    private static Boolean validateToken(TokenEntity tokenEntity) {
        // 토큰이 존재하지 않는 경우
        if (tokenEntity == null) {
            throw new ValidateException("토큰이 존재하지 않습니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        // 유효기간이 만료되었을 경우
        if (tokenEntity.getExpiresAt().isBefore(now)) {
            throw new ValidateException("유효기간이 만료되었습니다.");
        }
        // 이미 사용한 토큰인 경우
        if (BooleanUtils.isTrue(tokenEntity.getIsUse())) {
            throw new ValidateException("이미 사용이 완료된 토큰입니다.");
        }
        return true;
    }

    public Boolean changeTokenState(String token) {
        return tokenService.changeTokenState(token);
    }
}


