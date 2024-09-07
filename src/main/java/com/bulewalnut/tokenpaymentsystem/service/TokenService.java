package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import com.bulewalnut.tokenpaymentsystem.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final CardRepository cardRepository;

    @Transactional
    public void registerCard(CardDto cardDto, String refId) {
        cardRepository.save(CardEntity.of(cardDto, refId, "test123"));
    }
}
