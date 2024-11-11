import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';  // 导入 Home 组件
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';

const routes = [
    { path: '/', name: 'Home', component: Home },  // 默认路径指向 Home 组件
    { path: '/login', component: Login },
    { path: '/register', component: Register }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
