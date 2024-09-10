package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity;
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.bulewalnut.tokenpaymentsystem.exception.ValidateException;
import com.bulewalnut.tokenpaymentsystem.service.TokenService;
import com.bulewalnut.tokenpaymentsystem.type.MessageTypeEnum;
import com.bulewalnut.tokenpaymentsystem.util.RandomKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenApplication {

    private final TokenService tokenService;

    public String createRefIdAndRegisterCard(CardDto cardDto) {
        try {
            String refId = RandomKeyUtil.createRefId();
            tokenService.registerCard(cardDto, RandomKeyUtil.createRefId());
            return refId;
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.CARD_REGISTER_FAIL.getMessage(), e);
            throw new RestClientException(MessageTypeEnum.CARD_REGISTER_FAIL.getMessage());
        }
    }

    public List<CardDto> findCardByUserCi(String userCi) {
        List<CardEntity> cardEntities = tokenService.findCardByUserCi(userCi);

        if (cardEntities == null) {
            log.info(MessageTypeEnum.NOT_HAVE_CARD.getMessage(), userCi);
            return Collections.emptyList();
        }

        List<CardDto> cardDtos = new ArrayList<>();
        cardEntities.forEach(cardEntity -> cardDtos.add(CardDto.setCardDto(cardEntity.getRefId(), cardEntity.getCardNickName())));

        return cardDtos;
    }

    public String getTokenByRefId(String refId) {
        try {
            return tokenService.makeTokenAndSave(refId);
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.MAKE_TOKEN_FAIL.getMessage(), e);
            throw new RestClientException(MessageTypeEnum.MAKE_TOKEN_FAIL.getMessage());
        }
    }

    public Boolean findTokenEntityAndValidateToken(String token) {
        TokenEntity tokenEntity = tokenService.findTokenEntityByToken(token);
        return validateToken(tokenEntity);
    }

    private static Boolean validateToken(TokenEntity tokenEntity) {
        // 토큰이 존재하지 않는 경우
        if (tokenEntity == null) {
            log.error(MessageTypeEnum.VALIDATE_TOKEN_EXIST.getMessage());
            throw new ValidateException(MessageTypeEnum.VALIDATE_TOKEN_EXIST.getMessage());
        }
        LocalDateTime now = LocalDateTime.now();
        // 유효기간이 만료되었을 경우
        if (tokenEntity.getExpiresAt().isBefore(now)) {
            log.error(MessageTypeEnum.VALIDATE_TOKEN_EXPIRES_AT.getMessage());
            throw new ValidateException(MessageTypeEnum.VALIDATE_TOKEN_EXPIRES_AT.getMessage());
        }
        // 이미 사용한 토큰인 경우
        if (BooleanUtils.isTrue(tokenEntity.getIsUse())) {
            log.error(MessageTypeEnum.VALIDATE_TOKEN_IS_USE.getMessage());
            throw new ValidateException(MessageTypeEnum.VALIDATE_TOKEN_IS_USE.getMessage());
        }
        return true;
    }

    public Boolean changeTokenState(String token) {
        try {
            tokenService.changeTokenState(token);
            return true;
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.CHANGE_TOKEN_STATE_FAIL.getMessage(), e);
            throw new RestClientException(MessageTypeEnum.CHANGE_TOKEN_STATE_FAIL.getMessage());
        }
    }
}


