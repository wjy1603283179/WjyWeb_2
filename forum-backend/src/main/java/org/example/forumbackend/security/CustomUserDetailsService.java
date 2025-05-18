package org.example.forumbackend.security;

import lombok.RequiredArgsConstructor;
import org.example.forumbackend.entity.User;
import org.example.forumbackend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 自定义用户详情服务
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 尝试通过用户名查找用户
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    // 如果用户名不存在，尝试以邮箱查找
                    return userRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username));
                });
        
        // 检查用户状态
        if (!user.getStatus()) {
            throw new UsernameNotFoundException("User is disabled");
        }
        
        // 根据用户等级设置权限
        List<SimpleGrantedAuthority> authorities = getAuthorities(user.getLevel());
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getStatus(), // 启用状态
                true, // 账号未过期
                true, // 凭证未过期
                true, // 账号未锁定
                authorities
        );
    }
    
    /**
     * 根据用户等级获取权限列表
     */
    private List<SimpleGrantedAuthority> getAuthorities(Integer level) {
        if (level >= 100) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
} 