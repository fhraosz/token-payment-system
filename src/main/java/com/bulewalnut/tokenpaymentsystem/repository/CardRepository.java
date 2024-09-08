package com.bulewalnut.tokenpaymentsystem.repository;

import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findByUserCi(String userCi);
}
