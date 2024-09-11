package com.bulewalnut.tokenpaymentsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * 루트 경로로 접근 시 /main으로 리다이렉트
     */
    @GetMapping
    public String redirectToRegister() {
        return "redirect:/main";
    }

    /**
     * 메인 페이지로 이동
     */
    @GetMapping("/main")
    public String showMainPage() {
        return "main";
    }
}
