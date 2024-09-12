package com.bulewalnut.tokenpaymentsystem.api

import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto
import com.bulewalnut.tokenpaymentsystem.util.RestClient
import spock.lang.Specification
import spock.lang.Subject

class PaymentApiTest extends Specification {

    RestClient restClient = Mock()

    @Subject
    PaymentApi paymentApi = new PaymentApi(restClient)

    def "토큰 유효성 검사 API 호출 테스트"() {
        given:
        TokenRequestDto requestDto = new TokenRequestDto(token: "token123")
        paymentApi.tokenizationServiceUrl = "http://localhost:8081"

        when:
        Boolean result = paymentApi.validateToken(requestDto)

        then:
        1 * restClient.postRequest("http://localhost:8081/validate", requestDto) >> true
        result == true
    }

    def "토큰 상태 변경 API 호출 테스트"() {
        given:
        TokenRequestDto requestDto = new TokenRequestDto(token: "token123")
        paymentApi.tokenizationServiceUrl = "http://localhost:8081"

        when:
        Boolean result = paymentApi.changeStateToken(requestDto)

        then:
        1 * restClient.postRequest("http://localhost:8081/change/state", requestDto) >> true
        result == true
    }

    def "토큰 유효성 검사 실패 테스트"() {
        given:
        TokenRequestDto requestDto = new TokenRequestDto(token: "invalid_token")
        paymentApi.tokenizationServiceUrl = "http://localhost:8081"

        when:
        Boolean result = paymentApi.validateToken(requestDto)

        then:
        1 * restClient.postRequest("http://localhost:8081/validate", requestDto) >> false
        result == false
    }

    def "토큰 상태 변경 실패 테스트"() {
        given:
        TokenRequestDto requestDto = new TokenRequestDto(token: "invalid_token")
        paymentApi.tokenizationServiceUrl = "http://localhost:8081"

        when:
        Boolean result = paymentApi.changeStateToken(requestDto)

        then:
        1 * restClient.postRequest("http://localhost:8081/change/state", requestDto) >> false
        result == false
    }
}
