package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.TokenApplication;
import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto;
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

    /**
     * 카드 등록
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> registerCardByCardDto(@RequestBody CardDto cardDto) {
        return HttpBuilder.createResponse(tokenApplication.createRefIdAndRegisterCard(cardDto));
    }

    /**
     * 고객별 카드 목록 조회
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<CardDto>>>findCardByUserCi(@RequestParam("userCi") String userCi) {
        return HttpBuilder.createResponse(tokenApplication.findCardByUserCi(userCi));
    }

    /**
     * 결제를 위한 1회용 토큰 발급
     */
    @GetMapping("/token")
    public ResponseEntity<ResponseDto<String>> getTokenByRefId(@RequestParam("refId") String refId) {
        return HttpBuilder.createResponse(tokenApplication.getTokenByRefId(refId));
    }

    /**
     * 토큰 유효성 검사
     */
    @PostMapping("/validate")
    public ResponseEntity<ResponseDto<Boolean>> validateToken(@RequestBody TokenRequestDto requestDto) {
        return HttpBuilder.createResponse(tokenApplication.findTokenEntityAndValidateToken(requestDto.getToken()));
    }

    /**
     * 토큰 상태 변경
     */
    @PostMapping("/change/state")
    public ResponseEntity<ResponseDto<Boolean>> changeTokenState(@RequestBody TokenRequestDto requestDto) {
        return HttpBuilder.createResponse(tokenApplication.changeTokenState(requestDto.getToken()));
    }

}
