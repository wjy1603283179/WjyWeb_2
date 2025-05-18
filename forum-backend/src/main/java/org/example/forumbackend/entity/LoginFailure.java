package org.example.forumbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 登录失败记录实体类
 */
@Entity
@Table(name = "login_failures")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginFailure {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String ip;
    
    @Column(nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer count = 1;
    
    @Column(name = "last_failure_time", nullable = false)
    private LocalDateTime lastFailureTime;
    
    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
} 