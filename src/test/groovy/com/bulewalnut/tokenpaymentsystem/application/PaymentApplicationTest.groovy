package com.bulewalnut.tokenpaymentsystem.application

import com.bulewalnut.tokenpaymentsystem.api.PaymentApi
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto
import com.bulewalnut.tokenpaymentsystem.service.PaymentService
import spock.lang.Specification

class PaymentApplicationTest extends Specification {

    PaymentApi paymentApi = Mock()
    PaymentService paymentService = Mock()
    PaymentApplication paymentApplication = new PaymentApplication(paymentApi, paymentService)

    def "토큰이 유효하지 않으면 빈 결제 기록을 반환"() {
        given:
        PaymentDto paymentDto = new PaymentDto(token: "invalid_token")

        when:
        PaymentRecordDto result = paymentApplication.validateTokenAndProcessPayment(paymentDto)

        then:
        1 * paymentApi.validateToken(_ as TokenRequestDto) >> false
        result != null
        result.transactionId == null
    }

    def "토큰이 유효하면 결제가 처리된다"() {
        given:
        PaymentDto paymentDto = new PaymentDto(token: "valid_token")
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: "12345", amount: 1000)

        when:
        PaymentRecordDto result = paymentApplication.validateTokenAndProcessPayment(paymentDto)

        then:
        1 * paymentApi.validateToken(_ as TokenRequestDto) >> true
        1 * paymentService.processPayment(paymentDto) >> paymentRecordDto
        result.transactionId == "12345"
        result.amount == 1000
    }

    def "Transaction ID로 결제 기록을 조회할 수 있다"() {
        given:
        String transactionId = "12345"
        PaymentRecordDto paymentRecordDto = new PaymentRecordDto(transactionId: transactionId)

        when:
        PaymentRecordDto result = paymentApplication.findPaymentRecordByTransactionId(transactionId)

        then:
        1 * paymentService.findPaymentRecordByTransactionId(transactionId) >> paymentRecordDto
        result.transactionId == transactionId
    }
}
