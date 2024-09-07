package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.service.TokenService;
import com.bulewalnut.tokenpaymentsystem.util.RefIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenApplication {

    private final TokenService tokenService;

    public String createRefIdAndRegisterCard(CardDto cardDto) {
        String refId = RefIdUtil.createRefId();
        tokenService.registerCard(cardDto, refId);
        return refId;
    }
}


