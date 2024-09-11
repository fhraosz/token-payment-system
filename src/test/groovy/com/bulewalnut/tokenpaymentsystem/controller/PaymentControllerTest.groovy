package com.bulewalnut.tokenpaymentsystem.controller

import com.bulewalnut.tokenpaymentsystem.application.PaymentApplication
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto
import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject

class PaymentControllerTest extends Specification {

    PaymentApplication paymentApplication = Mock()

    @Subject
    PaymentController paymentController = new PaymentController(paymentApplication)

    def "결제 프로세스 테스트"() {
        given:
        PaymentDto paymentDto = new PaymentDto(amount: 1000, token: "token123")
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "tx123", amount: 1000)
        ResponseDto<PaymentRecordDto> responseDto = ResponseDto.setResponseDto(true, "결제 성공", paymentRecordDto)

        when:
        ResponseEntity<ResponseDto<PaymentRecordDto>> response = paymentController.paymentProcessByPayment(paymentDto)

        then:
        1 * paymentApplication.validateTokenAndProcessPayment(_ as PaymentDto) >> paymentRecordDto
        response.getBody().success == true
        response.getBody().data.transactionId == "tx123"
        response.getBody().data.amount == 1000
    }

    def "결제 내역 조회 테스트"() {
        given:
        String transactionId = "tx123"
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: transactionId, amount: 1000)
        ResponseDto<PaymentRecordDto> responseDto = ResponseDto.setResponseDto(true, "결제 내역 조회 성공", paymentRecordDto)

        when:
        ResponseEntity<ResponseDto<PaymentRecordDto>> response = paymentController.findPaymentRecordByTransactionId(transactionId)

        then:
        1 * paymentApplication.findPaymentRecordByTransactionId(transactionId) >> paymentRecordDto
        response.getBody().success == true
        response.getBody().data.transactionId == "tx123"
        response.getBody().data.amount == 1000
    }
}
