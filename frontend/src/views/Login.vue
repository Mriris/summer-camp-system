<template>
  <div class="login-page">
    <div class="login-card">
      <h3 class="text-center">用户登录</h3>
      <form @submit.prevent="login">
        <div class="form-floating mb-3">
          <input v-model="username" id="inputUsername" class="form-control" placeholder="用户名" required />
          <label for="inputUsername">用户名</label>
        </div>
        <div class="form-floating mb-3">
          <input v-model="password" id="inputPassword" type="password" class="form-control" placeholder="密码" required />
          <label for="inputPassword">密码</label>
        </div>
        <button class="btn btn-primary w-100" type="submit">登录</button>
      </form>
      <p v-if="message" class="text-danger text-center mt-3">{{ message }}</p>
    </div>
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
      try {
        const response = await axios.post('/users/login', {
          username: this.username,
          password: this.password,
        });
        this.message = '登录成功！3秒后跳转...';
        console.log('Login response:', response);
        // 3秒后跳转到 /CampApplication 页面
        setTimeout(() => {
          this.$router.push('/CampApplication');
        }, 3000);
      } catch (error) {
        console.error('Login error:', error);
        this.message = '登录失败: ' + (error.response?.data || '未知错误');
      }
    },
  },
};
</script>

<style scoped>
.login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding-top: 80px; /* 调整顶部间距，避免被导航栏遮挡 */
  box-sizing: border-box;
  background-color: #f8f9fa;
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 2rem;
  background: #fff;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
}
</style>
