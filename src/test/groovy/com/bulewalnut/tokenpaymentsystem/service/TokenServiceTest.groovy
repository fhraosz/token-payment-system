package com.bulewalnut.tokenpaymentsystem.service

import com.bulewalnut.tokenpaymentsystem.dto.CardDto
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity
import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity
import com.bulewalnut.tokenpaymentsystem.repository.CardRepository
import com.bulewalnut.tokenpaymentsystem.repository.TokenRepository
import com.bulewalnut.tokenpaymentsystem.util.RandomKeyUtil
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class TokenServiceTest extends Specification {

    CardRepository cardRepository = Mock()
    TokenRepository tokenRepository = Mock()

    @Subject
    TokenService tokenService = new TokenService(cardRepository, tokenRepository)

    def "카드 등록 테스트"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")
        String refId = "ref123"
        String userCi = "userCi123"

        when:
        tokenService.registerCard(cardDto, refId)

        then:
        1 * cardRepository.save(_ as CardEntity)
    }

    def "유저 CI로 카드 목록 조회 테스트 - 카드 존재"() {
        given:
        String userCi = "userCi123"
        List<CardEntity> cardEntityList = [new CardEntity(refId: "ref123")]

        when:
        List<CardEntity> result = tokenService.findCardByUserCi(userCi)

        then:
        1 * cardRepository.findByUserCi(userCi) >> cardEntityList
        result.size() == 1
        result[0].refId == "ref123"
    }

    def "유저 CI로 카드 목록 조회 테스트 - 카드 없음"() {
        given:
        String userCi = "userCi123"

        when:
        List<CardEntity> result = tokenService.findCardByUserCi(userCi)

        then:
        1 * cardRepository.findByUserCi(userCi) >> []
        result == null
    }

    def "토큰 생성 및 저장 테스트"() {
        given:
        String refId = "ref123"
        String generatedToken = RandomKeyUtil.createTokenByRefId(refId)

        when:
        String result = tokenService.makeTokenAndSave(refId)

        then:
        1 * tokenRepository.save(_ as TokenEntity)
        result != null
        generatedToken != null
    }


    def "토큰으로 TokenEntity 조회 테스트"() {
        given:
        String token = "token123"
        TokenEntity tokenEntity = new TokenEntity(token: token, refId: "ref123")

        when:
        TokenEntity result = tokenService.findTokenEntityByToken(token)

        then:
        1 * tokenRepository.findByToken(token) >> tokenEntity
        result.token == "token123"
        result.refId == "ref123"
    }

    def "토큰 상태 변경 테스트"() {
        given:
        String token = "token123"

        when:
        tokenService.changeTokenState(token)

        then:
        1 * tokenRepository.updateTokenStatusAndTimestamp(token, true)
    }
}
