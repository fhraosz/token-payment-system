package com.bulewalnut.tokenpaymentsystem.application

import com.bulewalnut.tokenpaymentsystem.dto.CardDto
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity
import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException
import com.bulewalnut.tokenpaymentsystem.exception.ValidateException
import com.bulewalnut.tokenpaymentsystem.service.TokenService
import com.bulewalnut.tokenpaymentsystem.type.MessageTypeEnum
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class TokenApplicationTest extends Specification {

    TokenService tokenService = Mock()

    @Subject
    TokenApplication tokenApplication = new TokenApplication(tokenService)

    def "카드 등록 및 참조값 생성 테스트"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")

        when:
        String result = tokenApplication.createRefIdAndRegisterCard(cardDto)

        then:
        1 * tokenService.registerCard(_ as CardDto, _ as String)
        result != null
    }

    def "카드 등록 실패 시 예외 처리"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123")

        when:
        tokenApplication.createRefIdAndRegisterCard(cardDto)

        then:
        1 * tokenService.registerCard(_ as CardDto, _ as String) >> { throw new RestClientException(MessageTypeEnum.CARD_REGISTER_FAIL.getMessage()) }
        thrown(RestClientException)
    }

    def "유저 CI로 카드 조회 테스트 - 카드 존재"() {
        given:
        String userCi = "userCi123"
        List<CardEntity> cardEntities = [new CardEntity(refId: "ref123", cardNickName: "MyCard")]

        when:
        List<CardDto> result = tokenApplication.findCardByUserCi(userCi)

        then:
        1 * tokenService.findCardByUserCi(userCi) >> cardEntities
        result.size() == 1
        result[0].cardNickName == "MyCard"
    }

    def "유저 CI로 카드 조회 테스트 - 카드 없음"() {
        given:
        String userCi = "userCi123"

        when:
        List<CardDto> result = tokenApplication.findCardByUserCi(userCi)

        then:
        1 * tokenService.findCardByUserCi(userCi) >> null
        result.isEmpty()
    }

    def "참조값으로 토큰 생성 테스트"() {
        given:
        String refId = "ref123"
        String token = "token123"

        when:
        String result = tokenApplication.getTokenByRefId(refId)

        then:
        1 * tokenService.makeTokenAndSave(refId) >> token
        result == token
    }

    def "토큰 유효성 검사 - 유효한 토큰"() {
        given:
        String token = "token123"
        TokenEntity tokenEntity = new TokenEntity(token: token, refId: "ref123", isUse: false, expiresAt: LocalDateTime.now().plusDays(1))

        when:
        Boolean result = tokenApplication.findTokenEntityAndValidateToken(token)

        then:
        1 * tokenService.findTokenEntityByToken(token) >> tokenEntity
        result == true
    }

    def "토큰 유효성 검사 실패 - 존재하지 않는 토큰"() {
        given:
        String token = "invalid_token"

        when:
        tokenApplication.findTokenEntityAndValidateToken(token)

        then:
        1 * tokenService.findTokenEntityByToken(token) >> null
        thrown(ValidateException)
    }

    def "토큰 상태 변경 성공 테스트"() {
        given:
        String token = "token123"

        when:
        Boolean result = tokenApplication.changeTokenState(token)

        then:
        1 * tokenService.changeTokenState(token)
        result == true
    }

    def "토큰 상태 변경 실패 시 예외 처리"() {
        given:
        String token = "token123"

        when:
        tokenApplication.changeTokenState(token)

        then:
        1 * tokenService.changeTokenState(token) >> { throw new RestClientException(MessageTypeEnum.CHANGE_TOKEN_STATE_FAIL.getMessage()) }
        thrown(RestClientException)
    }
}
