package com.bulewalnut.tokenpaymentsystem.repository;


import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
}
