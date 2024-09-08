package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.TokenApplication;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tokenization")
public class TokenController {

    private final TokenApplication tokenApplication;

    @PostMapping("/register")
    public String registerCardByCardDto(@RequestBody CardDto cardDto) {
        return tokenApplication.createRefIdAndRegisterCard(cardDto);
    }

    @GetMapping("/search")
    public List<CardDto> findCardByUserCi(@RequestParam("userCi") String userCi) {
        return tokenApplication.findCardByUserCi(userCi);
    }
}
