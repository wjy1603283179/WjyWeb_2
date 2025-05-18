<template>
  <div class="main-layout">
    <header class="app-header">
      <div class="logo-container">
        <router-link to="/" class="logo-link">
          <img src="@/assets/new_logo.svg" alt="论坛Logo" class="logo-img" />
          <span class="logo-text">爱人区</span>
        </router-link>
      </div>
      <nav class="main-nav">
        <router-link to="/" class="nav-link">首页</router-link>
        <router-link to="/tempstatic/posts" class="nav-link">staticPost</router-link>
        <!-- Add more links as needed, e.g., User Profile -->
      </nav>
      <div class="user-actions">
        <!-- Placeholder for login/register or user profile link -->
        <router-link to="/auth/login" class="nav-link auth-link">登录</router-link>
        <router-link to="/auth/register" class="nav-link auth-link">注册</router-link>
        <!-- <router-link to="/profile/1" class="nav-link">我的</router-link> -->
      </div>
    </header>
    <main class="app-main">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
    <footer class="app-footer">
      <p>&copy; {{ new Date().getFullYear() }} 爱人区. All rights reserved.</p>
      <p>
        <a href="#">关于我们</a> | <a href="#">联系方式</a> | <a href="#">隐私政策</a>
      </p>
    </footer>
  </div>
</template>

<script setup>
// Script for MainLayout
</script>

<style scoped>
.main-layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f4f6f8; /* Light background for the entire layout */
}

.app-header {
  background-color: #ffffff;
  padding: 0 2rem;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo-link {
  display: flex;
  align-items: center;
  text-decoration: none;
}

.logo-img {
  height: 40px;
  margin-right: 0.75rem;
  /* Ensure you have a logo.svg in src/assets or update path */
}

.logo-text {
  font-size: 1.8rem;
  font-weight: 700;
  color: #2c3e50;
}

.main-nav {
  display: flex;
  gap: 1.5rem;
}

.nav-link {
  text-decoration: none;
  color: #555;
  font-size: 1rem;
  font-weight: 500;
  padding: 0.5rem 0.75rem;
  border-radius: 5px;
  transition: color 0.3s ease, background-color 0.3s ease;
}

.nav-link:hover,
.nav-link.router-link-exact-active {
  color: #3498db;
  background-color: #ecf5ff;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.auth-link {
  border: 1px solid #3498db;
  padding: 0.4rem 0.8rem;
}

.auth-link:hover {
  background-color: #3498db;
  color: #fff;
}

.app-main {
  flex-grow: 1;
  padding: 2rem;
  /* background-color: #fff; /* Optional: if you want main content on white bg */
}

.app-footer {
  background-color: #34495e;
  color: #ecf0f1;
  padding: 2rem;
  text-align: center;
  font-size: 0.9rem;
}

.app-footer a {
  color: #3498db;
  text-decoration: none;
  margin: 0 0.5rem;
}

.app-footer a:hover {
  text-decoration: underline;
}

/* Router transition */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>