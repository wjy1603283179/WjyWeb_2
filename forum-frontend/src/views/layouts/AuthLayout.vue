<template>
  <div class="auth-layout">
    <main>
      <router-view />
    </main>
  </div>
</template>

<script setup>
// Script for AuthLayout
</script>

<style scoped>
.auth-layout {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f9f9f9; /* Light background for auth pages */
}
main {
  padding: 2rem;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px; /* Max width for the auth form container */
}
</style>