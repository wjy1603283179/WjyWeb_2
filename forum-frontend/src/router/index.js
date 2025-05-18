import { createRouter, createWebHistory } from 'vue-router';

// 布局组件
const MainLayout = () => import('../views/layouts/MainLayout.vue');
const AuthLayout = () => import('../views/layouts/AuthLayout.vue');

// 页面组件 - 路由懒加载
const HomePage = () => import('../views/home/HomePage.vue');
const LoginPage = () => import('../views/auth/LoginPage.vue');
const RegisterPage = () => import('../views/auth/RegisterPage.vue');
// 占位符，后续会添加更多页面
const PostListPage = () => import('../views/posts/PostListPage.vue');
const PostDetailPage = () => import('../views/posts/PostDetailPage.vue');
const PostCreatePage = () => import('../views/posts/PostCreatePage.vue');
const UserProfilePage = () => import('../views/user/UserProfilePage.vue');
const TempStaticPostDetailPage = () => import('../views/posts/TempStaticPostDetailPage.vue'); // 新增的静态帖子详情页

// 路由配置
const routes = [
  {
    path: '/',
    component: MainLayout,
    children: [
      {
        path: '',
        name: 'HomePage',
        component: HomePage,
        meta: { title: '首页 - 爱人区' }
      },
      {
        path: 'posts',
        name: 'PostList',
        component: PostListPage,
        meta: { title: '帖子列表 - 爱人区' }
      },
      {
        path: 'posts/create',
        name: 'PostCreate',
        component: PostCreatePage,
        meta: { title: '发表新帖 - 爱人区', requiresAuth: true }
      },
      {
        path: 'posts/:id',
        name: 'PostDetail',
        component: PostDetailPage,
        meta: { title: '帖子详情 - 爱人区', requiresAuth: false }
      },
      {
        path: 'tempstatic/posts', // 新增的静态帖子详情页路由
        name: 'TempStaticPostDetail',
        component: TempStaticPostDetailPage,
        meta: { title: '静态帖子预览 - 爱人区', requiresAuth: false }
      },
      {
        path: 'profile/:userId',
        name: 'UserProfile',
        component: UserProfilePage,
        meta: { title: '用户资料 - 爱人区', requiresAuth: true }
      }
    ]
  },
  {
    path: '/auth',
    component: AuthLayout,
    children: [
      {
        path: 'login',
        name: 'LoginPage',
        component: LoginPage,
        meta: { title: '登录 - 爱人区', guest: true }
      },
      {
        path: 'register',
        name: 'RegisterPage',
        component: RegisterPage,
        meta: { title: '注册 - 爱人区', guest: true }
      }
    ]
  },
  // 捕获所有未匹配的路由，可以重定向到首页或404页面
  { 
    path: '/:pathMatch(.*)*', 
    name: 'NotFound', 
    redirect: '/' // 或者指向一个专门的404页面组件
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL), // Vue CLI 项目使用 process.env.BASE_URL
  routes
});

// 全局前置守卫
router.beforeEach((to, from, next) => {
  // 设置标题
  document.title = to.meta.title ? `${to.meta.title}` : '爱人区';
  
  // 获取认证状态 (简单示例，实际应用中会更复杂)
  const isLoggedIn = !!localStorage.getItem('token');
  
  // 需要认证的页面
  if (to.meta.requiresAuth && !isLoggedIn) {
    next({ name: 'LoginPage', query: { redirect: to.fullPath } });
    return;
  }
  
  // 只允许游客访问的页面（如登录、注册）
  if (to.meta.guest && isLoggedIn) {
    next({ name: 'HomePage' }); // 如果已登录，访问登录/注册页则跳转到首页
    return;
  }
  
  next();
});

export default router;