<!-- src/views/Login.vue -->
<template>
  <div class="login">
    <h2>用户登录</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">用户名:</label>
        <input v-model="username" id="username" required />
      </div>
      <div>
        <label for="password">密码:</label>
        <input v-model="password" id="password" type="password" required />
      </div>
      <button type="submit">登录</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      message: '',
    };
  },
  methods: {
    async login() {
      // 打印发送的数据到控制台
      console.log('发送登录：', {
        username: this.username,
        password: this.password,
      });

      try {
        const response = await axios.post('/users/login', {
          username: this.username,
          password: this.password,
        });
        this.message = '登录成功！';
        console.log('Login response:', response); // 打印响应内容到控制台
        // 这里可以将用户信息存储在本地存储或 Vuex 中
      } catch (error) {
        console.error('Login error:', error); // 打印错误到控制台
        this.message = '登录失败: ' + (error.response?.data || '未知错误');
      }
    },
  },
};
</script>

<style scoped>
/* 可以在这里添加你的样式 */
</style>
