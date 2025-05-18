import axios from './axios';

/**
 * 认证相关API
 */
export default {
  /**
   * 用户注册
   * @param {Object} data - 注册数据
   * @param {string} data.username - 用户名
   * @param {string} data.password - 密码
   * @param {string} data.email - 邮箱
   * @returns {Promise<Object>} 响应数据
   */
  register(data) {
    return axios.post('/auth/register', data);
  },
  
  /**
   * 用户登录
   * @param {Object} data - 登录数据
   * @param {string} data.username - 用户名
   * @param {string} data.password - 密码
   * @returns {Promise<Object>} 响应数据
   */
  login(data) {
    return axios.post('/auth/login', data);
  },
  
  /**
   * 获取当前用户信息
   * @returns {Promise<Object>} 响应数据
   */
  getCurrentUser() {
    return axios.get('/auth/current-user');
  }
}; 