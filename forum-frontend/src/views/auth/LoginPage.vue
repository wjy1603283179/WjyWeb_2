<template>
  <div class="login-page">
    <h2>登录</h2>
    <form @submit.prevent="handleLogin">
      <div>
        <label for="email">邮箱:</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div>
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <button type="submit">登录</button>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </form>
    <p>
      还没有账户? <router-link to="/auth/register">立即注册</router-link>
    </p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const email = ref('');
const password = ref('');
const router = useRouter();
const errorMessage = ref(''); // 用于显示错误信息

const handleLogin = async () => {
  errorMessage.value = ''; // 重置错误信息
  try {
    // 注意：API端点应与后端配置一致，这里假设为 /api/v1/auth/login
    // 在 vue.config.js 中配置了代理，所以可以直接使用 /api
    const response = await axios.post('/api/v1/auth/login', {
      email: email.value, // 或者 username，取决于后端API设计
      password: password.value
    });

    if (response.data && response.data.token) {
      localStorage.setItem('token', response.data.token);
      // 根据后端返回的用户信息，可以考虑存储更多用户信息到localStorage或Pinia/Vuex
      // 例如：localStorage.setItem('user', JSON.stringify(response.data.user));
      router.push(router.currentRoute.value.query.redirect || '/'); // 跳转到首页或重定向地址
    } else {
      // 如果后端没有返回token，或者返回的格式不符合预期
      errorMessage.value = response.data.message || '登录失败，请检查您的凭据。';
    }
  } catch (error) {
    console.error('Login failed:', error);
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage.value = error.response.data.message;
    } else if (error.request) {
      errorMessage.value = '无法连接到服务器，请稍后再试。';
    } else {
      errorMessage.value = '登录过程中发生未知错误。';
    }
  }
};
</script>

<style scoped>
.error-message {
  color: red;
  text-align: center;
  margin-top: 1rem;
}

.login-page {
  max-width: 350px;
  margin: auto;
  padding: 2rem;
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
}

form div {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #555;
}

input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

button[type="submit"] {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

button[type="submit"]:hover {
  background-color: #0056b3;
}

p {
  text-align: center;
  margin-top: 1rem;
  color: #666;
}

a {
  color: #007bff;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>