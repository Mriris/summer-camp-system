<!-- src/views/Register.vue -->
<template>
  <div class="register-page bg-primary">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-7">
          <div class="card shadow-lg border-0 rounded-lg mt-5">
            <div class="card-header">
              <h3 class="text-center font-weight-light my-4">创建账号</h3>
            </div>
            <div class="card-body">
              <form @submit.prevent="register">
                <div class="form-floating mb-3">
                  <input v-model="username" id="inputUsername" class="form-control" type="text" placeholder="输入您的用户名" required />
                  <label for="inputUsername">用户名</label>
                </div>
                <div class="form-floating mb-3">
                  <input v-model="email" id="inputEmail" class="form-control" type="email" placeholder="name@example.com" required />
                  <label for="inputEmail">邮箱地址</label>
                </div>
                <div class="form-floating mb-3">
                  <input v-model="password" id="inputPassword" class="form-control" type="password" placeholder="创建一个密码" required />
                  <label for="inputPassword">密码</label>
                </div>
                <div class="form-floating mb-3">
                  <input v-model="passwordConfirm" id="inputPasswordConfirm" class="form-control" type="password" placeholder="确认密码" required />
                  <label for="inputPasswordConfirm">确认密码</label>
                </div>
                <div class="mt-4 mb-0">
                  <button class="btn btn-primary w-100" type="submit">创建账号</button>
                </div>
              </form>
              <p v-if="message" class="text-danger text-center mt-3">{{ message }}</p>
            </div>
            <div class="card-footer text-center py-3">
              <div class="small">
                <router-link to="/login">已经有账号？前往登录</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'Register',
  data() {
    return {
      username: '', // 更改字段名为 username
      email: '',
      password: '',
      passwordConfirm: '',
      message: '',
    };
  },
  methods: {
    async register() {
      // 检查密码是否匹配
      if (this.password !== this.passwordConfirm) {
        this.message = '密码和确认密码不匹配';
        return;
      }

      try {
        const response = await axios.post('/users/register', {
          username: this.username, // 使用后端接受的字段名 username
          email: this.email,
          password: this.password,
        });

        this.message = '注册成功！3秒后跳转至登录页面...';

        // 3秒后跳转到登录页面
        setTimeout(() => {
          this.$router.push('/login');
        }, 3000);
      } catch (error) {
        console.error('Registration error:', error);
        this.message = '注册失败: ' + (error.response?.data?.message || '未知错误');
      }
    },
  },
};
</script>

<style scoped>
.register-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding-top: 80px;
}

.card {
  max-width: 500px;
  margin: auto;
}

.bg-primary {
  background-color: #007bff !important;
}
</style>
