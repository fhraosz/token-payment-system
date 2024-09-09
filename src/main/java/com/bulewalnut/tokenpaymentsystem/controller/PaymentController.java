package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.PaymentApplication;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentApplication paymentApplication;

    // 카드결제
    @PostMapping("/process")
    public String paymentProcessByPayment(@RequestBody PaymentDto paymentDto) {
        return "test";
    }

}
