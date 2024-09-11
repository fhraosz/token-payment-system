package com.bulewalnut.tokenpaymentsystem.controller

import com.bulewalnut.tokenpaymentsystem.application.CardApplication
import com.bulewalnut.tokenpaymentsystem.dto.CardDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto
import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import spock.lang.Specification

class CardControllerTest extends Specification {

    CardApplication cardApplication = Mock()
    CardController cardController = new CardController(cardApplication)

    def "카드 등록 페이지 호출 테스트"() {
        given:
        Model model = Mock()

        when:
        String viewName = cardController.showCardRegister(model)

        then:
        1 * model.addAttribute(CardController.CARD_DTO, _ as CardDto)
        viewName == CardController.REGISTER_CARD
    }

    def "카드 등록 API 테스트"() {
        given:
        CardDto cardDto = new CardDto(cardNumber: "1234-1231-1231-1231", cardExpiry: "12/25", cardCvc: "123", cardNickName: "TestCard")
        ResponseDto<String> responseDto  = ResponseDto.setResponseDto(true, "등록 성공", "카드가 성공적으로 등록되었습니다.")

        when:
        ResponseEntity<ResponseDto<String>> response = cardController.registerCard(cardDto)

        then:
        1 * cardApplication.encryptAndRegisterCard(*_) >> responseDto
        response.getBody().success
        response.getBody().message == "등록 성공"
    }

    def "카드 결제 처리 테스트"() {
        given:
        PaymentDto paymentDto = new PaymentDto(amount: 1000, token: "token123")
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "tx123", amount: 1000)

        ResponseDto<PaymentRecordDto> responseDto = ResponseDto.setResponseDto(true, "결제 성공", paymentRecordDto)

        when:
        ResponseEntity<ResponseDto<PaymentRecordDto>> response = cardController.paymentProcessByToken(paymentDto)

        then:
        1 * cardApplication.paymentProcessByToken(*_) >> responseDto
        response.getBody().success == true
        response.getBody().message == "결제 성공"
        response.getBody().data.transactionId == "tx123"
    }


    def "결제 성공 페이지 이동 테스트"() {
        given:
        Model model = Mock()
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "tx123", amount: 1000)

        when:
        String viewName = cardController.showPaymentComplete("tx123", model)

        then:
        1 * cardApplication.findPaymentRecordByTransactionId(*_) >> paymentRecordDto
        1 * model.addAttribute(CardController.PAYMENT_RECORD_DTO, _ as PaymentRecordDto)
        viewName == CardController.PAYMENT_COMPLETE
    }
}
