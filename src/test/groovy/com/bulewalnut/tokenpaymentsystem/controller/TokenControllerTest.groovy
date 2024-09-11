package com.bulewalnut.tokenpaymentsystem.controller

import com.bulewalnut.tokenpaymentsystem.application.TokenApplication
import com.bulewalnut.tokenpaymentsystem.dto.CardDto
import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject

class TokenControllerTest extends Specification {

    TokenApplication tokenApplication = Mock()

    @Subject
    TokenController tokenController = new TokenController(tokenApplication)

    def "카드 등록 테스트"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "TestCard")

        when:
        ResponseEntity<ResponseDto<String>> response = tokenController.registerCardByCardDto(cardDto)

        then:
        1 * tokenApplication.createRefIdAndRegisterCard(_ as CardDto) >> "ref123"
        response.getBody().success == true
        response.getBody().data == "ref123"
    }

    def "고객별 카드 목록 조회 테스트"() {
        given:
        String userCi = "userCi123"
        List<CardDto> cardDtos = [new CardDto(cardNumber: "1234", cardNickName: "TestCard")]

        when:
        ResponseEntity<ResponseDto<List<CardDto>>> response = tokenController.findCardByUserCi(userCi)

        then:
        1 * tokenApplication.findCardByUserCi(userCi) >> cardDtos
        response.getBody().success == true
        response.getBody().data.size() == 1
        response.getBody().data[0].cardNickName == "TestCard"
    }

    def "1회용 토큰 발급 테스트"() {
        given:
        String refId = "ref123"
        ResponseDto<String> responseDto = ResponseDto.setResponseDto(true, "토큰 발급 성공", "token123")

        when:
        ResponseEntity<ResponseDto<String>> response = tokenController.getTokenByRefId(refId)

        then:
        1 * tokenApplication.getTokenByRefId(refId) >> "token123"
        response.getBody().success == true
        response.getBody().data == "token123"
    }

    def "토큰 유효성 검사 테스트"() {
        given:
        TokenRequestDto requestDto = new TokenRequestDto(token: "token123")

        when:
        ResponseEntity<ResponseDto<Boolean>> response = tokenController.validateToken(requestDto)

        then:
        1 * tokenApplication.findTokenEntityAndValidateToken(requestDto.token) >> true
        response.getBody().success == true
        response.getBody().data == true
    }

    def "토큰 상태 변경 테스트"() {
        given:
        TokenRequestDto requestDto = new TokenRequestDto(token: "token123")

        when:
        ResponseEntity<ResponseDto<Boolean>> response = tokenController.changeTokenState(requestDto)

        then:
        1 * tokenApplication.changeTokenState(requestDto.token) >> true
        response.getBody().success == true
        response.getBody().data == true
    }
}