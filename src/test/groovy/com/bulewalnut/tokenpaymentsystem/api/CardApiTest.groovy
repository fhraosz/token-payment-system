package com.bulewalnut.tokenpaymentsystem.api

import com.bulewalnut.tokenpaymentsystem.dto.CardDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto
import com.bulewalnut.tokenpaymentsystem.exception.EncryptionException
import com.bulewalnut.tokenpaymentsystem.util.EncryptionUtil
import com.bulewalnut.tokenpaymentsystem.util.RestClient
import org.springframework.core.ParameterizedTypeReference
import spock.lang.Specification

class CardApiTest extends Specification {

    RestClient restClient = Mock()
    EncryptionUtil encryptionUtil = Mock()
    CardApi cardApi = new CardApi(encryptionUtil, restClient)

    def "카드 등록 실패 테스트 - 암호화 실패"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")

        encryptionUtil.encrypt(cardDto.cardNumber) >> { throw new EncryptionException("Encryption failed") }

        when:
        cardApi.encryptAndRegisterCard(cardDto)

        then:
        thrown(EncryptionException)
    }

    def "카드 목록 조회 테스트"() {
        given:
        List<CardDto> cardList = [new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")]

        when:
        List<CardDto> result = cardApi.findCardByUserCi("userCi123")

        then:
        1 * restClient.getListRequest(_) >> cardList
        result.size() == 1
        result[0].cardNumber == "1234"
    }

    def "결제 처리 성공 테스트"() {
        given:
        PaymentDto paymentDto = new PaymentDto(amount: 1000, refId: "ref123")
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "tx123", amount: 1000)

        when:
        PaymentRecordDto result = cardApi.paymentProcessByToken(paymentDto)

        then:
        1 * restClient.postRequest(_ as String, paymentDto, _ as ParameterizedTypeReference) >> paymentRecordDto
        result.transactionId == "tx123"
        result.amount == 1000
    }

    def "결제 기록 조회 테스트"() {
        given:
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "tx123", amount: 1000)

        when:
        PaymentRecordDto result = cardApi.findPaymentRecordByTransactionId("tx123")

        then:
        1 * restClient.postRequest(_ as String, "tx123", _ as ParameterizedTypeReference) >> paymentRecordDto
        result.transactionId == "tx123"
        result.amount == 1000
    }
}
