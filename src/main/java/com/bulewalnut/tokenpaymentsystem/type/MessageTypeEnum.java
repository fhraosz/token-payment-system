package com.bulewalnut.tokenpaymentsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    CARD_REGISTER_SUCCESS("카드가 성공적으로 등록되었습니다."),
    CARD_REGISTER_FAIL("카드 등록에 실패하였습니다."),
    CARD_REGISTER_FAIL_WITH_MESSAGE("카드 등록 과정에서 문제가 발생했습니다. 다시 시도 해주세요."),
    PAYMENT_PROCESS_SUCCESS("결제에 성공하였습니다."),
    PAYMENT_PROCESS_FAIL("결제 처리에 실패하였습니다."),
    PAYMENT_PROCESS_FAIL_WITH_MESSAGE("결제 처리에 실패하였습니다. 다시 시도해 주세요."),
    RESPONSE_ENTITY_EXCEPTION("외부 서비스와 통신 실패."),
    RESPONSE_ENTITY_REST_CLIENT_EXCEPTION("외부 서비스와의 통신 중 알 수 없는 오류 발생."),
    MAKE_REF_ID_FAIL("refID의 값이 없습니다."),
    MAKE_TOKEN_FAIL("토큰 생성에 실패하였습니다."),
    CHANGE_TOKEN_STATE_FAIL("토큰 상태 변경에 실패하였습니다."),
    CHANGE_TOKEN_STATE_SUCCESS("토큰 상태 변경에 성공하였습니다."),
    VALIDATE_TOKEN_EXIST("토큰이 존재하지 않습니다."),
    VALIDATE_TOKEN_EXPIRES_AT("토큰의 유효기간이 만료되었습니다."),
    VALIDATE_TOKEN_IS_USE("이미 사용이 완료된 토큰입니다."),
    NOT_HAVE_CARD("등록한 카드가 없습니다."),
    ENCRYPT_FAIL("암호화에 실패하였습니다.");

    private final String message;
}
