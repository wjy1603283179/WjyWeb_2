<template>
  <div class="temp-static-post-detail-page">
    <article class="post-container">
      <header class="post-header">
        <h1 class="post-title">{{ post.title }}</h1>
        <div class="post-meta">
          <router-link to="/posts" class="back-to-posts-link"><i class="fas fa-arrow-left"></i> 返回帖子列表</router-link>
          <span class="author">作者: {{ post.author.name }}</span>
          <span class="publish-date">发布于: {{ formatDate(post.createdAt) }}</span>
          <span class="category" v-if="post.category">分类: {{ post.category }}</span>
        </div>
      </header>

      <div class="post-content" v-html="post.content"></div>

      <footer class="post-footer">
        <div class="actions">
          <button @click="toggleLike" :class="{ liked: post.isLiked }">
            <i :class="post.isLiked ? 'fas fa-heart' : 'far fa-heart'"></i> {{ post.likes }} 赞
          </button>
          <button @click="scrollToComments">
            <i class="far fa-comment-dots"></i> {{ post.commentsCount }} 评论
          </button>
          <button @click="sharePost">
            <i class="fas fa-share-alt"></i> 分享
          </button>
        </div>
        <div class="tags" v-if="post.tags && post.tags.length">
          <span v-for="tag in post.tags" :key="tag" class="tag">#{{ tag }}</span>
        </div>
      </footer>
    </article>

    <section class="comments-section" id="comments-section">
      <h2 class="section-title">评论区 ({{ post.commentsCount }})</h2>
      <div class="comment-form">
        <textarea v-model="newComment" placeholder="发表你的看法..."></textarea>
        <button @click="submitComment">发表评论</button>
      </div>
      <div class="comment-list" v-if="comments.length">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <div class="comment-author-avatar">
            <img :src="comment.author.avatarUrl || 'https://via.placeholder.com/40'" alt="avatar">
          </div>
          <div class="comment-content">
            <p class="comment-author-name">{{ comment.author.name }} <span class="comment-date">{{ formatDate(comment.createdAt) }}</span></p>
            <p class="comment-text">{{ comment.text }}</p>
            <div class="comment-actions">
              <button @click="replyToComment(comment)"><i class="fas fa-reply"></i> 回复</button>
            </div>
          </div>
        </div>
      </div>
      <p v-else class="no-comments">暂无评论，快来抢沙发吧！</p>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 模拟帖子数据
const post = ref({
  id: 'temp123',
  title: '探索Vue 3 Composition API的奥秘',
  author: {
    id: 'user001',
    name: '爱学习的张三',
    avatarUrl: 'https://via.placeholder.com/50/007bff/ffffff?Text=UserA'
  },
  createdAt: new Date('2024-03-10T10:30:00Z'),
  updatedAt: new Date('2024-03-11T14:45:00Z'),
  category: '技术分享',
  content: `
    <p>Vue 3 的 Composition API 是一项革命性的特性，它为我们组织和复用代码逻辑提供了前所未有的灵活性。与传统的 Options API 不同，Composition API 允许我们根据逻辑功能来组织代码，而不是按照选项（data, methods, computed等）来分散代码。</p>
    <img src="https://via.placeholder.com/600x300/eee/aaa?text=Vue+3+Composition+API" alt="Vue 3 Composition API" style="max-width:100%; border-radius: 8px; margin: 1rem 0;">
    <p><strong>主要优势包括：</strong></p>
    <ul>
      <li>更好的逻辑复用：通过自定义 Hooks (composables) 轻松提取和复用状态逻辑。</li>
      <li>更灵活的代码组织：相关代码可以放在一起，提高可读性和可维护性。</li>
      <li>更好的 TypeScript 支持：Composition API 的设计对 TypeScript 非常友好。</li>
    </ul>
    <p>让我们深入探讨一些常用的 Composition API 函数，如 <code>ref</code>, <code>reactive</code>, <code>computed</code>, <code>watch</code>, 以及生命周期钩子。</p>
    <pre><code class="language-javascript">
// 示例：一个简单的计数器 composable
import { ref } from 'vue';

export function useCounter(initialValue = 0) {
  const count = ref(initialValue);
  const increment = () => count.value++;
  const decrement = () => count.value--;
  return { count, increment, decrement };
}
    </code></pre>
    <p>在组件中使用：</p>
    <pre><code class="language-javascript">
import { useCounter } from './composables/useCounter';

export default {
  setup() {
    const { count, increment } = useCounter();
    return { count, increment };
  }
};
    </code></pre>
    <p>这只是冰山一角，Composition API 的强大之处远不止于此。希望这篇文章能帮助你更好地理解和运用它！</p>
  `,
  tags: ['Vue3', 'JavaScript', '前端开发', 'CompositionAPI'],
  likes: 128,
  isLiked: false,
  commentsCount: 15,
});

// 模拟评论数据
const comments = ref([

]);

const newComment = ref('');

const formatDate = (date) => {
  if (!date) return '';
  return new Date(date).toLocaleString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' });
};

const toggleLike = () => {
  post.value.isLiked = !post.value.isLiked;
  post.value.likes += post.value.isLiked ? 1 : -1;
  // 实际应用中会调用API更新点赞状态
};

const scrollToComments = () => {
  const commentsSection = document.getElementById('comments-section');
  if (commentsSection) {
    commentsSection.scrollIntoView({ behavior: 'smooth' });
  }
};

const sharePost = () => {
  // 实际分享逻辑，例如使用 Web Share API 或弹出分享链接
  alert('分享功能待实现！链接：' + window.location.href);
};

const submitComment = () => {
  if (!newComment.value.trim()) {
    alert('评论内容不能为空！');
    return;
  }
  const newId = 'comment' + (comments.value.length + 1).toString().padStart(3, '0');
  comments.value.unshift({
    id: newId,
    author: { id: 'currentUser', name: '当前用户', avatarUrl: 'https://via.placeholder.com/40/6c757d/ffffff?Text=Me' }, // 模拟当前用户
    createdAt: new Date(),
    text: newComment.value
  });
  post.value.commentsCount++;
  newComment.value = '';
  // 实际应用中会调用API提交评论
};

const replyToComment = (comment) => {
  newComment.value = `@${comment.author.name} `;
  // 聚焦到评论输入框等交互
  document.querySelector('.comment-form textarea').focus();
};

onMounted(() => {
  // 可以在这里添加一些页面加载后的动效，例如元素入场动画
  // 使用 Prism.js 或其他库高亮代码块 (如果页面中包含代码)
  if (window.Prism) {
    window.Prism.highlightAll();
  }
});

</script>

<style scoped>
.temp-static-post-detail-page {
  max-width: 900px;
  margin: 2rem auto;
  padding: 1rem;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  animation: fadeIn 0.5s ease-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.post-container {
  padding: 2rem;
}

.post-header {
  border-bottom: 1px solid #e9ecef;
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
}

.post-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #2c3e50; /* 主题深色 */
  margin-bottom: 0.75rem;
  line-height: 1.3;
}

.post-meta {
  font-size: 0.9rem;
  color: #7f8c8d; /* 次要文本颜色 */
}

.post-meta span, .back-to-posts-link {
  margin-right: 1rem;
}

.back-to-posts-link {
  color: #3498db; /* 链接颜色 */
  text-decoration: none;
  display: inline-block;
  margin-bottom: 0.5rem; /* 与下方内容保持一些间距 */
}

.back-to-posts-link:hover {
  text-decoration: underline;
}

.post-meta span {
  margin-right: 1rem;
}

.post-meta .author a {
  color: #3498db; /* 链接颜色 */
  text-decoration: none;
}
.post-meta .author a:hover {
  text-decoration: underline;
}

.post-content {
  font-size: 1.1rem;
  line-height: 1.8;
  color: #34495e; /* 正文颜色 */
  word-wrap: break-word;
}

.post-content ::v-deep(p) {
  margin-bottom: 1.2em;
}

.post-content ::v-deep(h2, h3, h4) {
  margin-top: 2em;
  margin-bottom: 0.8em;
  color: #2c3e50;
}

.post-content ::v-deep(ul, ol) {
  margin-left: 1.5em;
  margin-bottom: 1em;
}

.post-content ::v-deep(blockquote) {
  margin: 1.5em 0;
  padding: 0.5em 1em;
  border-left: 4px solid #3498db;
  background-color: #f8f9fa;
  color: #555;
}

.post-content ::v-deep(pre) {
  background-color: #2d2d2d; /* 深色代码块背景 */
  color: #f8f8f2; /* 代码颜色 */
  padding: 1em;
  border-radius: 6px;
  overflow-x: auto;
  margin: 1.5em 0;
  font-family: 'Fira Code', 'Courier New', Courier, monospace;
}

.post-content ::v-deep(code) {
  font-family: 'Fira Code', 'Courier New', Courier, monospace;
  background-color: #f0f0f0; /* 行内代码背景 */
  padding: 0.2em 0.4em;
  border-radius: 3px;
  font-size: 0.9em;
}

.post-content ::v-deep(pre code) {
  background-color: transparent;
  padding: 0;
  font-size: 1em;
}

.post-footer {
  margin-top: 2.5rem;
  padding-top: 1.5rem;
  border-top: 1px solid #e9ecef;
}

.actions button {
  background-color: transparent;
  border: 1px solid #bdc3c7; /* 边框颜色 */
  color: #34495e;
  padding: 0.6rem 1.2rem;
  margin-right: 0.75rem;
  border-radius: 20px; /* 圆角按钮 */
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 0.9rem;
}

.actions button:hover {
  background-color: #f0f2f5; /* 轻微背景变化 */
  border-color: #3498db;
  color: #3498db;
}

.actions button.liked {
  background-color: #e74c3c; /* 点赞后的颜色 */
  border-color: #e74c3c;
  color: white;
}

.actions button.liked:hover {
  background-color: #c0392b;
}

.actions button i {
  margin-right: 0.5rem;
}

.tags {
  margin-top: 1.5rem;
}

.tag {
  display: inline-block;
  background-color: #ecf0f1; /* 标签背景 */
  color: #7f8c8d;
  padding: 0.3rem 0.8rem;
  border-radius: 15px;
  margin-right: 0.5rem;
  margin-bottom: 0.5rem;
  font-size: 0.85rem;
  transition: background-color 0.3s ease;
}

.tag:hover {
  background-color: #bdc3c7;
  color: #2c3e50;
}

.comments-section {
  margin-top: 3rem;
  padding: 2rem;
  background-color: #f8f9fa; /* 评论区背景 */
  border-radius: 8px;
}

.section-title {
  font-size: 1.8rem;
  color: #2c3e50;
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #3498db;
  padding-bottom: 0.5rem;
  display: inline-block;
}

.comment-form textarea {
  width: 100%;
  min-height: 100px;
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 6px;
  margin-bottom: 1rem;
  font-size: 1rem;
  resize: vertical;
  transition: border-color 0.3s ease;
}

.comment-form textarea:focus {
  border-color: #3498db;
  outline: none;
  box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
}

.comment-form button {
  background-color: #3498db;
  color: white;
  padding: 0.7rem 1.5rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.comment-form button:hover {
  background-color: #2980b9;
}

.comment-list {
  margin-top: 2rem;
}

.comment-item {
  display: flex;
  margin-bottom: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e9ecef;
  animation: slideInUp 0.5s ease-out;
}

.comment-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
}

@keyframes slideInUp {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: translateY(0); }
}

.comment-author-avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 1rem;
}

.comment-content {
  flex-grow: 1;
}

.comment-author-name {
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 0.3rem;
}

.comment-date {
  font-size: 0.8rem;
  color: #95a5a6; /* 日期颜色 */
  margin-left: 0.5rem;
}

.comment-text {
  font-size: 1rem;
  line-height: 1.6;
  color: #34495e;
  margin-bottom: 0.5rem;
}

.comment-actions button {
  background: none;
  border: none;
  color: #3498db;
  cursor: pointer;
  font-size: 0.85rem;
  padding: 0.2rem 0.5rem;
}

.comment-actions button:hover {
  text-decoration: underline;
}

.comment-actions button i {
  margin-right: 0.3rem;
}

.no-comments {
  text-align: center;
  color: #7f8c8d;
  padding: 2rem 0;
  font-style: italic;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .post-title {
    font-size: 2rem;
  }
  .post-content {
    font-size: 1rem;
  }
  .actions button {
    padding: 0.5rem 1rem;
    font-size: 0.85rem;
    margin-bottom: 0.5rem; /* 堆叠时增加间距 */
  }
  .comments-section {
    padding: 1.5rem;
  }
  .section-title {
    font-size: 1.5rem;
  }
}

@media (max-width: 480px) {
  .temp-static-post-detail-page {
    margin: 1rem;
    padding: 0.5rem;
  }
  .post-container, .comments-section {
    padding: 1rem;
  }
  .post-title {
    font-size: 1.75rem;
  }
  .post-meta span, .back-to-posts-link {
  margin-right: 1rem;
}

.back-to-posts-link {
  color: #3498db; /* 链接颜色 */
  text-decoration: none;
  display: inline-block;
  margin-bottom: 0.5rem; /* 与下方内容保持一些间距 */
}

.back-to-posts-link:hover {
  text-decoration: underline;
}

.post-meta span {
    display: block; /* 在小屏幕上元数据堆叠显示 */
    margin-right: 0;
    margin-bottom: 0.3rem;
  }
  .actions {
    display: flex;
    flex-direction: column; /* 动作按钮垂直排列 */
  }
  .actions button {
    width: 100%;
    margin-right: 0;
  }
}
</style>