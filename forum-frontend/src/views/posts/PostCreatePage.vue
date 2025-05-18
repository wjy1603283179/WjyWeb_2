<template>
  <div class="post-create-page">
    <h2>发表新帖</h2>
    <form @submit.prevent="handleCreatePost">
      <div>
        <label for="title">标题:</label>
        <input type="text" id="title" v-model="title" required>
      </div>
      <div>
        <label for="content">内容:</label>
        <textarea id="content" v-model="content" rows="10" required></textarea>
      </div>
      <button type="submit">发布帖子</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
// import { useRouter } from 'vue-router'; // Comment out or remove unused import

const title = ref('');
const content = ref('');
// const router = useRouter(); // Comment out or remove unused variable

const handleCreatePost = () => {
  // 实际创建帖子的逻辑将在这里实现
  console.log('Creating post with:', title.value, content.value);
  // 假设创建成功，跳转到帖子列表或新创建的帖子详情页
  // 在实际应用中，这里会调用API
  // router.push('/posts'); // 或者 router.push(`/posts/${newPostId}`);
  alert('帖子创建功能待实现！');
};
</script>

<style scoped>
.post-create-page {
  max-width: 700px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

form div {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #555;
  font-weight: bold;
}

input[type="text"],
textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem;
}

textarea {
  resize: vertical;
}

button[type="submit"] {
  display: block;
  width: 100%;
  padding: 0.8rem 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1.1rem;
  transition: background-color 0.2s ease-in-out;
}

button[type="submit"]:hover {
  background-color: #0056b3;
}
</style>