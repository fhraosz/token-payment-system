package com.bulewalnut.tokenpaymentsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "token", indexes = {
        @Index(name = "idx_token_1", columnList = "token, ref_id")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private Long tokenId;

    @Column(name = "token", nullable = false, unique = true, length = 100)
    private String token;

    @Column(name = "ref_id", nullable = false, length = 100)
    private String refId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_use", nullable = false)
    private Boolean isUse;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static TokenEntity buildTokenEntity(String token, String refId, LocalDateTime now) {
        return TokenEntity.builder()
                .token(token)
                .refId(refId)
                .expiresAt(now.plusMinutes(10))
                .isUse(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
