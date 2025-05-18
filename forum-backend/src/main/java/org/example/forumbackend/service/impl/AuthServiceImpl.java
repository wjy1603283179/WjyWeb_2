package org.example.forumbackend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.forumbackend.dto.AuthResponse;
import org.example.forumbackend.dto.LoginRequest;
import org.example.forumbackend.dto.RegisterRequest;
import org.example.forumbackend.dto.UserDTO;
import org.example.forumbackend.entity.LoginFailure;
import org.example.forumbackend.entity.User;
import org.example.forumbackend.exception.AppException;
import org.example.forumbackend.exception.BadRequestException;
import org.example.forumbackend.repository.LoginFailureRepository;
import org.example.forumbackend.repository.UserRepository;
import org.example.forumbackend.security.JwtUtil;
import org.example.forumbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 认证服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final LoginFailureRepository loginFailureRepository;
    
    @Value("${app.login.max-fail-attempts:3}")
    private int maxFailAttempts;
    
    @Value("${app.login.lock-duration-minutes:1}")
    private int lockDurationMinutes;
    
    @Value("${app.jwt.expiration:86400000}")
    private long jwtExpiration;
    
    @Override
    @Transactional
    public UserDTO register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("邮箱已被注册");
        }
        
        // 创建新用户
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .level(1) // 默认普通用户
                .status(true) // 默认启用
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
        
        // 保存用户
        user = userRepository.save(user);
        
        // 返回用户DTO
        return mapUserToDTO(user);
    }
    
    @Override
    @Transactional
    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        String ip = getClientIp(httpRequest);
        String username = request.getUsername();
        
        // 检查是否被锁定
        Optional<LoginFailure> loginFailureOpt = loginFailureRepository.findByIpAndUsername(ip, username);
        if (loginFailureOpt.isPresent()) {
            LoginFailure loginFailure = loginFailureOpt.get();
            
            // 检查是否被锁定且锁定未过期
            if (loginFailure.getLockedUntil() != null && loginFailure.getLockedUntil().isAfter(LocalDateTime.now())) {
                throw new LockedException("账户已被锁定，请" + lockDurationMinutes + "分钟后再试");
            }
        }
        
        try {
            // 尝试认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, request.getPassword())
            );
            
            // 认证成功，设置安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 生成JWT令牌
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);
            
            // 获取用户信息
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new AppException(500, "用户不存在"));
            
            // 更新最后登录时间和IP
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(ip);
            userRepository.save(user);
            
            // 清除登录失败记录
            loginFailureOpt.ifPresent(loginFailureRepository::delete);
            
            // 返回认证响应
            return AuthResponse.builder()
                    .token(jwt)
                    .tokenType("Bearer")
                    .expiresIn(jwtExpiration / 1000) // 转换为秒
                    .user(mapUserToDTO(user))
                    .build();
            
        } catch (DisabledException e) {
            throw new DisabledException("账户已被禁用");
        } catch (LockedException e) {
            throw new LockedException("账户已被锁定");
        } catch (Exception e) {
            log.error("登录失败", e);
            
            // 记录登录失败
            recordLoginFailure(ip, username);
            
            // 重新抛出异常
            throw e;
        }
    }
    
    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new BadRequestException("用户未登录");
        }
        
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(500, "当前登录用户不存在"));
        
        return mapUserToDTO(user);
    }
    
    /**
     * 记录登录失败
     */
    private void recordLoginFailure(String ip, String username) {
        LoginFailure loginFailure = loginFailureRepository.findByIpAndUsername(ip, username)
                .orElse(LoginFailure.builder()
                        .ip(ip)
                        .username(username)
                        .count(0)
                        .lastFailureTime(LocalDateTime.now())
                        .build());
        
        // 增加失败次数
        loginFailure.setCount(loginFailure.getCount() + 1);
        loginFailure.setLastFailureTime(LocalDateTime.now());
        
        // 如果失败次数达到阈值，则锁定
        if (loginFailure.getCount() >= maxFailAttempts) {
            loginFailure.setLockedUntil(LocalDateTime.now().plusMinutes(lockDurationMinutes));
        }
        
        loginFailureRepository.save(loginFailure);
    }
    
    /**
     * 获取客户端IP
     */
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
    
    /**
     * 将User实体映射为UserDTO
     */
    private UserDTO mapUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .signature(user.getSignature())
                .level(user.getLevel())
                .status(user.getStatus())
                .lastLoginTime(user.getLastLoginTime())
                .createdAt(user.getCreatedAt())
                .build();
    }
} 