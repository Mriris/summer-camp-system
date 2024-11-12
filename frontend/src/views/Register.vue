<!-- src/views/Register.vue -->
<template>
  <div class="register">
    <h2>用户注册</h2>
    <form @submit.prevent="register">
      <div>
        <label for="username">用户名:</label>
        <input v-model="username" id="username" required />
      </div>
      <div>
        <label for="email">邮箱:</label>
        <input v-model="email" id="email" type="email" required />
      </div>
      <div>
        <label for="password">密码:</label>
        <input v-model="password" id="password" type="password" required />
      </div>
      <button type="submit">注册</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'Register',
  data() {
    return {
      username: '',
      email: '',
      password: '',
      message: '',
    };
  },
  methods: {
    async register() {
      try {
        const response = await axios.post('/users/register', {
          username: this.username,
          email: this.email,
          password: this.password,
        });
        this.message = '注册成功！';
      } catch (error) {
        this.message = '注册失败: ' + (error.response?.data?.message || '未知错误');
      }
    },
  },
};
</script>

<style scoped>
/* 可以在这里添加你的样式 */
</style>
