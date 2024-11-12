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
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'Login',
  setup() {
    const store = useStore();
    const router = useRouter();

    return {
      store,
      router,
    };
  },
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

        // 检查后端返回的状态码是否成功
        if (response.status === 200) {
          this.store.dispatch('login', this.username);
          this.message = '登录成功！3秒后跳转...';
          console.log('Login response:', response);

          // 3秒后跳转到 /CampApplication 页面
          setTimeout(() => {
            this.router.push('/CampApplication');
          }, 3000);
        } else {
          this.message = '登录失败: 非预期的响应';
        }
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
  padding-top: 80px;
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
