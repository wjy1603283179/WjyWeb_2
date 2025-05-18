package org.example.forumbackend.repository;

import org.example.forumbackend.entity.LoginFailure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 登录失败记录数据仓库
 */
@Repository
public interface LoginFailureRepository extends JpaRepository<LoginFailure, Long> {
    
    /**
     * 根据IP和用户名查找登录失败记录
     */
    Optional<LoginFailure> findByIpAndUsername(String ip, String username);
} 