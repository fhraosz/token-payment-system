package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.TokenApplication;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tokenization")
public class TokenController {

    private final TokenApplication tokenApplication;

    // 카드등록
    @PostMapping("/register")
    public String registerCardByCardDto(@RequestBody CardDto cardDto) {
        return tokenApplication.createRefIdAndRegisterCard(cardDto);
    }

    // 고객이 등록한 카드 전부 가져오기
    @GetMapping("/search")
    public List<CardDto> findCardByUserCi(@RequestParam("userCi") String userCi) {
        return tokenApplication.findCardByUserCi(userCi);
    }

    // 1회용 토큰 발급
    @GetMapping("/token")
    public String getTokenByRefId(@RequestParam("refId") String refId) {
        return tokenApplication.getTokenByRefId(refId);
    }
}
