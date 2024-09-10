package com.bulewalnut.tokenpaymentsystem.repository;


import com.bulewalnut.tokenpaymentsystem.entity.PaymentRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecordEntity, Long> {

    PaymentRecordEntity findByTransactionId(String transactionId);
}
