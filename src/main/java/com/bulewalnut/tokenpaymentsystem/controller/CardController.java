package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.CardApplication;
import com.bulewalnut.tokenpaymentsystem.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.bulewalnut.tokenpaymentsystem.util.ResponseUtil;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    public static final String CARD_DTO = "cardDto";
    public static final String PAYMENT_RECORD_DTO = "paymentRecordDto";
    public static final String REGISTER_CARD = "register-card";
    public static final String PAYMENT_CARD = "payment-card";
    public static final String PAYMENT_COMPLETE = "payment-complete";
    public static final String CARD_LIST = "cardList";

    private final CardApplication cardApplication;

    /**
     * 카드 등록 페이지
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute(CARD_DTO, new CardDto());
        return REGISTER_CARD;
    }

    /**
     * 카드 등록
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> registerCard(@RequestBody CardDto cardDto) {
        ResponseDto<String> result = cardApplication.encryptAndRegisterCard(cardDto);
        return ResponseUtil.createResponse(result);
    }

    /**
     * 카드 결제 페이지
     */
    @GetMapping("/payment")
    public String getCardList(Model model) {
        List<CardDto> cardList = cardApplication.findCardByUserCi();
        model.addAttribute(CARD_LIST, cardList);
        return PAYMENT_CARD;
    }

    /**
     * 카드 결제 처리를 수행하고 결제 결과 리턴
     */
    @PostMapping("/payment/process")
    public ResponseEntity<ResponseDto<PaymentRecordDto>> processPayment(@RequestBody PaymentDto paymentDto) {
        ResponseDto<PaymentRecordDto> result = cardApplication.paymentProcessByToken(paymentDto);
        return ResponseUtil.createResponse(result);
    }

    @GetMapping("/payment/complete/{transactionId}")
    public String showPaymentComplete(@PathVariable String transactionId, Model model) {
        PaymentRecordDto paymentRecordDto = cardApplication.findPaymentRecordByTransactionId(transactionId);
        model.addAttribute(PAYMENT_RECORD_DTO, paymentRecordDto);
        return PAYMENT_COMPLETE;
    }
}
