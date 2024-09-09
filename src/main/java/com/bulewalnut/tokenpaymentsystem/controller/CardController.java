package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.CardApplication;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardApplication cardApplication;

    // 카드등록페이지
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("cardDto", new CardDto());
        return "register-card";
    }

    // 카드등록
    @PostMapping("/register")
    public String registerCard(CardDto cardDto, Model model) {
        String refId = cardApplication.encryptAndRegisterCard(cardDto);

        if (refId == null) {
            model.addAttribute("error", "카드 등록 과정에서 문제가 발생했습니다.");
            return "register-card";
        }

        model.addAttribute("message", "카드가 성공적으로 등록되었습니다. REF_ID: " + refId);
        return "register-card";
    }

    // 카드결제페이지
    @GetMapping("/payment")
    public String getCardList(Model model) {
        List<CardDto> cardList = cardApplication.findCardByUserCi();
        model.addAttribute("cardList", cardList);
        return "payment-card";
    }

    // 카드결제하기
    @PostMapping("/payment/process")
    public String processCard(@ModelAttribute PaymentDto paymentDto, Model model) {
        String result = cardApplication.paymentProcessByPaymentDto(paymentDto);

        if (result == null) {
            model.addAttribute("error", "결제 처리에 실패하였습니다. 다시 시도해 주세요.");
            return "payment-card";
        }

        model.addAttribute("message", "결제가 성공적으로 처리되었습니다.");
        return "payment-card";
    }
}
