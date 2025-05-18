package org.example.forumbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.forumbackend.dto.AuthResponse;
import org.example.forumbackend.dto.LoginRequest;
import org.example.forumbackend.dto.RegisterRequest;
import org.example.forumbackend.dto.UserDTO;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 用户注册
     */
    UserDTO register(RegisterRequest request);
    
    /**
     * 用户登录
     */
    AuthResponse login(LoginRequest request, HttpServletRequest httpRequest);
    
    /**
     * 获取当前认证用户信息
     */
    UserDTO getCurrentUser();
} 