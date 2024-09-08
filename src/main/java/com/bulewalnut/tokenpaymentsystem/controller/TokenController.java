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
        return tokenApplication.createRefIdAndRegisterCard(cardDto);
    }
}
