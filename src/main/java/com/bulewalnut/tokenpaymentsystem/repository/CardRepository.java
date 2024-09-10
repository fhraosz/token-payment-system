package com.bulewalnut.tokenpaymentsystem.repository;

import com.bulewalnut.tokenpaymentsystem.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findByUserCi(String userCi);
}
