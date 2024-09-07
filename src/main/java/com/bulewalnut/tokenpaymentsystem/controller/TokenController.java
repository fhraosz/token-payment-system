package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.TokenApplication;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tokenization")
public class TokenController {

    private final TokenApplication tokenApplication;

    @PostMapping("/register")
    public String registerCard(@RequestBody CardDto cardDto) {
        tokenApplication.createRefIdAndRegisterCard(cardDto);
        // 카드 정보를 처리하고 REF_ID를 생성하여 반환
        return generateRefId(); // 실제 참조값 생성 로직 필요
    }

    private String generateRefId() {
        // 카드 참조값 생성 로직 (임의로 생성한 값)
        return "REF123456789";
    }
}
