<template>
  <div class="register-page">
    <h2>注册新账户</h2>
    <form @submit.prevent="handleRegister">
      <div>
        <label for="username">用户名:</label>
        <input type="text" id="username" v-model="username" required>
      </div>
      <div>
        <label for="email">邮箱:</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div>
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <div>
        <label for="confirmPassword">确认密码:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required>
      </div>
      <button type="submit">注册</button>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
    </form>
    <p>
      已有账户? <router-link to="/auth/login">立即登录</router-link>
    </p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const username = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const router = useRouter();
const errorMessage = ref(''); // 用于显示错误信息
const successMessage = ref(''); // 用于显示成功信息

const handleRegister = async () => {
  errorMessage.value = ''; // 重置错误信息
  successMessage.value = ''; // 重置成功信息

  if (password.value !== confirmPassword.value) {
    errorMessage.value = '两次输入的密码不一致！';
    return;
  }

  try {
    // 注意：API端点应与后端配置一致，这里假设为 /api/v1/auth/register
    // 在 vue.config.js 中配置了代理，所以可以直接使用 /api
    const response = await axios.post('/api/v1/auth/register', {
      username: username.value,
      email: email.value,
      password: password.value
    });

    if (response.data && response.data.message) { // 假设后端成功时返回类似 { message: '注册成功' } 的结构
      successMessage.value = response.data.message + ' 即将跳转到登录页面...';
      // 注册成功后，可以给用户一些提示，然后延时跳转到登录页
      setTimeout(() => {
        router.push('/auth/login');
      }, 3000); // 3秒后跳转
    } else {
      // 如果后端返回的格式不符合预期
      errorMessage.value = response.data.message || '注册失败，请稍后再试。';
    }
  } catch (error) {
    console.error('Registration failed:', error);
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage.value = error.response.data.message;
    } else if (error.request) {
      errorMessage.value = '无法连接到服务器，请稍后再试。';
    } else {
      errorMessage.value = '注册过程中发生未知错误。';
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

.success-message {
  color: green;
  text-align: center;
  margin-top: 1rem;
}

.register-page {
  max-width: 380px;
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

input[type="text"],
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
  background-color: #28a745; /* Green for registration */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

button[type="submit"]:hover {
  background-color: #218838;
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