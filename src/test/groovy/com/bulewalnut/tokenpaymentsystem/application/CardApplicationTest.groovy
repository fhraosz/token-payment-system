package com.bulewalnut.tokenpaymentsystem.application

import com.bulewalnut.tokenpaymentsystem.api.CardApi
import com.bulewalnut.tokenpaymentsystem.dto.CardDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto
import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto
import com.bulewalnut.tokenpaymentsystem.exception.EncryptionException
import com.bulewalnut.tokenpaymentsystem.type.MessageTypeEnum
import spock.lang.Specification
import spock.lang.Subject

class CardApplicationSpec extends Specification {

    CardApi cardApi = Mock()

    @Subject
    CardApplication cardApplication = new CardApplication(cardApi)

    def "카드 등록 성공 테스트"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")

        when:
        ResponseDto<String> result = cardApplication.encryptAndRegisterCard(cardDto)

        then:
        1 * cardApi.encryptAndRegisterCard(*_) >> "ref123"
        result.success == true
        result.message.contains(MessageTypeEnum.CARD_REGISTER_SUCCESS.getMessage())
        result.data == "ref123"
    }

    def "카드 등록 실패 테스트 - refId null"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")
        cardApi.encryptAndRegisterCard(cardDto) >> null

        when:
        ResponseDto<String> result = cardApplication.encryptAndRegisterCard(cardDto)

        then:
        1 * cardApi.encryptAndRegisterCard(cardDto)
        result.success == false
        result.message == MessageTypeEnum.CARD_REGISTER_FAIL_WITH_MESSAGE.getMessage()
        result.data == null
    }

    def "카드 등록 실패 테스트 - EncryptionException 발생"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")
        cardApi.encryptAndRegisterCard(cardDto) >> { throw new EncryptionException("Encryption failed") }

        when:
        ResponseDto<String> result = cardApplication.encryptAndRegisterCard(cardDto)

        then:
        1 * cardApi.encryptAndRegisterCard(cardDto)
        result.success == false
        result.message == MessageTypeEnum.CARD_REGISTER_FAIL_WITH_MESSAGE.getMessage()
        result.data == null
    }

    def "카드 목록 조회 테스트"() {
        given:
        List<CardDto> cardList = [new CardDto(cardNumber: "1234", cardExpiry: "12/25", cardCvc: "123", cardNickName: "MyCard")]
        def userCi = "testUserCi"
        cardApi.findCardByUserCi(userCi) >> cardList

        when:
        cardApplication.findCardByUserCi()

        then:
        1 * cardApi.findCardByUserCi(*_) >> cardList
    }

    def "결제 처리 성공 테스트"() {
        given:
        PaymentDto paymentDto = new PaymentDto(amount: 1000, refId: "ref123")
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "tx123", amount: 1000)

        when:
        ResponseDto<PaymentRecordDto> result = cardApplication.paymentProcessByToken(paymentDto)

        then:
        1 * cardApi.getTokenByRefId(*_) >> "token123"
        1 * cardApi.paymentProcessByToken(*_) >> paymentRecordDto
        result.success == true
        result.message == MessageTypeEnum.PAYMENT_PROCESS_SUCCESS.getMessage()
        result.data.transactionId == "tx123"
    }

    def "결제 처리 실패 테스트 - 토큰 발급 실패"() {
        given:
        PaymentDto paymentDto = new PaymentDto(amount: 1000, refId: "ref123")

        when:
        ResponseDto<PaymentRecordDto> result = cardApplication.paymentProcessByToken(paymentDto)

        then:
        1 * cardApi.getTokenByRefId(*_) >> null
        result.success == false
        result.message == MessageTypeEnum.PAYMENT_PROCESS_FAIL_WITH_MESSAGE.getMessage()
        result.data == null
    }

    def "결제 기록 조회 테스트"() {
        given:
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "tx123", amount: 1000)

        when:
        PaymentRecordDto result = cardApplication.findPaymentRecordByTransactionId("tx123")

        then:
        1 * cardApi.findPaymentRecordByTransactionId(*_) >> paymentRecordDto
        result.transactionId == "tx123"
        result.amount == 1000
    }
}
