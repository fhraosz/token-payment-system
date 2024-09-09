package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.TokenApplication;
import com.bulewalnut.tokenpaymentsystem.dto.ApiResponse;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto;
import com.bulewalnut.tokenpaymentsystem.util.HttpBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tokenization")
public class TokenController {

    private final TokenApplication tokenApplication;

    // 카드등록
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerCardByCardDto(@RequestBody CardDto cardDto) {
        return HttpBuilder.createResponse(tokenApplication.createRefIdAndRegisterCard(cardDto));
    }

    // 고객이 등록한 카드 가져오기
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CardDto>>>findCardByUserCi(@RequestParam("userCi") String userCi) {
        return HttpBuilder.createResponse(tokenApplication.findCardByUserCi(userCi));
    }

    // 1회용 토큰 발급
    @GetMapping("/token")
    public ResponseEntity<ApiResponse<String>> getTokenByRefId(@RequestParam("refId") String refId) {
        return HttpBuilder.createResponse(tokenApplication.getTokenByRefId(refId));
    }

    // 토큰 유효성 검사
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestBody TokenRequestDto requestDto) {
        return HttpBuilder.createResponse(tokenApplication.findTokenEntityAndValidateToken(requestDto.getToken()));
    }

    // 토큰 상태변경
    @PostMapping("/change/state")
    public ResponseEntity<ApiResponse<Boolean>> changeTokenState(@RequestBody TokenRequestDto requestDto) {
        return HttpBuilder.createResponse(tokenApplication.changeTokenState(requestDto.getToken()));
    }

}
