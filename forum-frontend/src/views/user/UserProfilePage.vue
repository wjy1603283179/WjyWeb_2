<template>
  <div class="user-profile-page">
    <h2>用户个人资料</h2>
    <div v-if="user">
      <p><strong>用户名:</strong> {{ user.username }}</p>
      <p><strong>邮箱:</strong> {{ user.email }}</p>
      <!-- Add more user details here -->
    </div>
    <div v-else>
      <p>正在加载用户数据...</p>
    </div>
    <!-- Placeholder for edit profile link, user's posts, etc. -->
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

// Mock user data - replace with API call
const mockUsers = {
  '1': { id: '1', username: '示例用户1', email: 'user1@example.com' },
  '2': { id: '2', username: '示例用户2', email: 'user2@example.com' },
};

const user = ref(null);
const route = useRoute();

onMounted(() => {
  const userId = route.params.userId;
  // 在实际应用中，这里会根据 userId 调用API获取用户数据
  if (mockUsers[userId]) {
    user.value = mockUsers[userId];
  } else {
    console.error('User not found');
    // Optionally redirect to a not-found page or show an error message
  }
});
</script>

<style scoped>
.user-profile-page {
  padding: 2rem;
  max-width: 600px;
  margin: 2rem auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

p {
  margin-bottom: 1rem;
  color: #555;
  font-size: 1.1rem;
}

strong {
  color: #333;
}
</style>