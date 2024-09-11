package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPaymentListDto {

    private String transactionId; // 결제내역ID
    private String paymentCompleteDate; // 결제완료일
    private String status; // 결제여부
    private String refId; // refID
    private String userCi; // 고객구분값
}
