package com.bulewalnut.tokenpaymentsystem.service

import com.bulewalnut.tokenpaymentsystem.api.PaymentApi
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto
import com.bulewalnut.tokenpaymentsystem.entity.PaymentRecordEntity
import com.bulewalnut.tokenpaymentsystem.exception.PaymentException
import com.bulewalnut.tokenpaymentsystem.repository.PaymentRecordRepository
import com.bulewalnut.tokenpaymentsystem.type.PaymentStateEnum
import spock.lang.Specification
import spock.lang.Subject

class PaymentServiceTest extends Specification {

    PaymentRecordRepository paymentRecordRepository = Mock()
    PaymentApi paymentApi = Mock()

    @Subject
    PaymentService paymentService = new PaymentService(paymentRecordRepository, paymentApi)

    def "결제 성공 처리 테스트"() {
        given:
        PaymentDto paymentDto = new PaymentDto(token: "valid_token", amount: 1000)
        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity(transactionId: "tx123", amount: 1000, status: PaymentStateEnum.APPROVED.getState())

        when:
        PaymentRecordDto result = paymentService.processPayment(paymentDto)

        then:
        1 * paymentRecordRepository.save(_ as PaymentRecordEntity) >> paymentRecordEntity
        1 * paymentApi.changeStateToken(_ as TokenRequestDto) >> true
        result.transactionId == "tx123"
        result.amount == 1000
        result.status == PaymentStateEnum.APPROVED.getState()
    }

    def "결제 실패 시 예외 처리 테스트"() {
        given:
        PaymentDto paymentDto = new PaymentDto(token: "invalid_token", amount: 1000)

        when:
        paymentService.processPayment(paymentDto)

        then:
        1 * paymentRecordRepository.save(_ as PaymentRecordEntity) >> { throw new PaymentException("결제 실패") }
        thrown(PaymentException)
    }

    def "Transaction ID로 결제 기록을 성공적으로 조회"() {
        given:
        String transactionId = "tx123"
        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity(transactionId: transactionId, amount: 1000)

        when:
        PaymentRecordDto result = paymentService.findPaymentRecordByTransactionId(transactionId)

        then:
        1 * paymentRecordRepository.findByTransactionId(transactionId) >> paymentRecordEntity
        result.transactionId == transactionId
        result.amount == 1000
    }

    def "Transaction ID로 결제 기록 조회 실패 시 빈 PaymentRecordDto 반환"() {
        given:
        String transactionId = "invalid_tx"

        when:
        PaymentRecordDto result = paymentService.findPaymentRecordByTransactionId(transactionId)

        then:
        1 * paymentRecordRepository.findByTransactionId(transactionId) >> null
        result.transactionId == null
    }
}
