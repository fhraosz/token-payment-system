package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.CardApplication;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
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
        if (ObjectUtils.isEmpty(cardDto)) {
            model.addAttribute("error", "CardDto is null");
            return "register-card";
        }
        String refId = cardApplication.encryptAndSendCardDto(cardDto);
        model.addAttribute("message", "Card registered with REF_ID: " + refId);
        return "register-card";
    }
}
