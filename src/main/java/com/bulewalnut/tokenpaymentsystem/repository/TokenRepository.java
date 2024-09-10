package com.bulewalnut.tokenpaymentsystem.repository;


import com.bulewalnut.tokenpaymentsystem.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity findByToken(String token);

    @Modifying
    @Query("UPDATE TokenEntity t SET t.isUse = :isUse WHERE t.token = :token")
    void updateTokenStatusAndTimestamp(String token, Boolean isUse);
}
