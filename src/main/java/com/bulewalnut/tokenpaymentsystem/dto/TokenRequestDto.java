package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {

    private String token;

    public static TokenRequestDto of(String token) {
        return TokenRequestDto.builder()
                .token(token)
                .build();
    }
}
