package com.bulewalnut.tokenpaymentsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "token")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "ref_id", nullable = false)
    private String refId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_use", nullable = false)
    private boolean isUse;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static TokenEntity of(String refId) {
        LocalDateTime now = LocalDateTime.now();

        return TokenEntity.builder()
                .token(UUID.randomUUID().toString())
                .refId(refId)
                .expiresAt(now.plusMinutes(10))
                .isUse(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
