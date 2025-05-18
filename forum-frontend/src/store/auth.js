import { defineStore } from 'pinia';
import authApi from '../api/auth';

/**
 * 用户认证状态管理
 */
export const useAuthStore = defineStore('auth', {
  // 状态
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || null,
    isLoggedIn: !!localStorage.getItem('token'),
    loading: false,
    error: null
  }),
  
  // Getter
  getters: {
    isAdmin: (state) => state.user?.level === 100,
    userLevel: (state) => state.user?.level || 0
  },
  
  // Actions
  actions: {
    /**
     * 用户注册
     */
    async register(userData) {
      this.loading = true;
      this.error = null;
      try {
        const response = await authApi.register(userData);
        this.user = response.data;
        return response;
      } catch (error) {
        this.error = error.response?.data?.message || '注册失败';
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    /**
     * 用户登录
     */
    async login(credentials) {
      this.loading = true;
      this.error = null;
      try {
        const response = await authApi.login(credentials);
        const { token, user } = response.data;
        
        // 保存token到localStorage
        localStorage.setItem('token', token);
        
        // 更新状态
        this.token = token;
        this.user = user;
        this.isLoggedIn = true;
        
        return response;
      } catch (error) {
        this.error = error.response?.data?.message || '登录失败';
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    /**
     * 用户退出登录
     */
    logout() {
      // 清除localStorage
      localStorage.removeItem('token');
      
      // 重置状态
      this.token = null;
      this.user = null;
      this.isLoggedIn = false;
    },
    
    /**
     * 获取当前用户信息
     */
    async fetchCurrentUser() {
      if (!this.token) return;
      
      this.loading = true;
      try {
        const response = await authApi.getCurrentUser();
        this.user = response.data;
        return response;
      } catch (error) {
        // 如果出现401错误，将在axios拦截器中处理
        this.error = error.response?.data?.message || '获取用户信息失败';
        throw error;
      } finally {
        this.loading = false;
      }
    }
  }
}); 