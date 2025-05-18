package org.example.forumbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private String signature;
    private Integer level;
    private Boolean status;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createdAt;
} 