package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.CardApplication;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardApplication cardApplication;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("cardDto", new CardDto());
        return "register-card";
    }

    @PostMapping("/register")
    public String registerCard(CardDto cardDto, Model model) {
        String refId = cardApplication.encryptAndSendCardDto(cardDto);

        if (refId == null) {
            model.addAttribute("error", "카드 등록 과정에서 문제가 발생했습니다.");
            return "register-card";
        }

        model.addAttribute("message", "카드가 성공적으로 등록되었습니다. REF_ID: " + refId);
        return "register-card";
    }
}
