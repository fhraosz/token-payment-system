package com.bulewalnut.tokenpaymentsystem.repository;

import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
