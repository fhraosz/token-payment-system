package com.bulewalnut.tokenpaymentsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    CARD_REGISTER_SUCCESS("카드가 성공적으로 등록되었습니다."),
    CARD_REGISTER_FAIL("카드 등록 과정에서 문제가 발생했습니다. 다시 시도 해주세요."),
    PAYMENT_PROCESS_SUCCESS("결제에 성공하였습니다."),
    PAYMENT_PROCESS_FAIL("결제 처리에 실패하였습니다. 다시 시도해 주세요."),
    RESPONSE_ENTITY_EXCEPTION("외부 서비스와 통신 실패."),
    RESPONSE_ENTITY_REST_CLIENT_EXCEPTION("외부 서비스와의 통신 중 알 수 없는 오류 발생."),
    MAKE_REF_ID_FAIL("refID의 값이 없습니다.");

    private final String message;
}
