<template>
  <div class="login-page">
    <div class="login-card">
      <h3 class="text-center">用户登录</h3>
      <form @submit.prevent="login">
        <div class="form-floating mb-3">
          <input
              v-model="emailOrIdNumber"
              id="inputEmailOrIdNumber"
              class="form-control"
              placeholder="邮箱或身份证号"
              required
          />
          <label for="inputEmailOrIdNumber">邮箱或身份证号</label>
        </div>
        <div class="form-floating mb-3">
          <input
              v-model="password"
              id="inputPassword"
              type="password"
              class="form-control"
              placeholder="密码"
              required
          />
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
      emailOrIdNumber: '',
      password: '',
      message: '',
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/users/login', {
          emailOrIdNumber: this.emailOrIdNumber,
          password: this.password,
        });

        if (response.status === 200 && response.data.username) {
          const username = response.data.username;
          const userId = response.data.id;
          // 将用户名存储到 Vuex 和 localStorage
          this.store.dispatch('login', username);
          localStorage.setItem('userId', userId);
          // 设置登录成功消息，并跳转
          this.message = '登录成功！1秒后跳转...';
          setTimeout(() => {
            this.router.push('/CampApplication');
          }, 1000);
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
