package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDto {

    private String token;

    public static TokenRequestDto setTokenRequestDto(String token) {
        TokenRequestDto tokenRequestDto = new TokenRequestDto();
        tokenRequestDto.setToken(token);
        return tokenRequestDto;
    }

}
