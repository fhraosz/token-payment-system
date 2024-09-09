package com.bulewalnut.tokenpaymentsystem.repository;


import com.bulewalnut.tokenpaymentsystem.entity.PaymentRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecordEntity, Long> {
}
