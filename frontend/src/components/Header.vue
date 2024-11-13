<template>
  <header :class="['header', { 'no-margin-left': isNoMarginPage }]" class="d-flex align-items-center fixed-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">
      <a href="/" class="logo d-flex align-items-center me-auto">
        <h1 class="sitename">夏令营</h1>
      </a>

      <nav id="navmenu" class="navmenu">
        <ul>
          <li><router-link to="/" exact-active-class="active">主页</router-link></li>
          <li><a href="#faq-2" @click.prevent="navigateToFaq">FAQ</a></li>
          <li><a href="#contact" @click.prevent="navigateToContact">联系我们</a></li>
        </ul>
        <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
      </nav>

      <div class="auth-links" @mouseover="showAuthOptions = true" @mouseleave="showAuthOptions = false">
        <router-link v-if="username" class="btn-getstarted" to="/profile">{{ username }}</router-link>
        <router-link v-else class="btn-getstarted" to="/login">登录</router-link>

        <!-- 使用 transition 包裹悬停项以实现渐显动画 -->
        <transition name="fade-slide">
          <div v-if="showAuthOptions" class="dropdown-options">
            <router-link v-if="username" to="/" @click="logout">登出</router-link>
            <router-link v-else to="/register">注册</router-link>
          </div>
        </transition>
      </div>
    </div>
  </header>
</template>

<script>
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute, useRouter } from 'vue-router';

export default {
  name: 'Header',
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
    const showAuthOptions = ref(false);

    const username = computed(() => store.state.username);
    const isNoMarginPage = computed(() => {
      return ['Home', 'Login', 'Register'].includes(route.name);
    });

    const navigateToFaq = () => {
      router.push({ name: 'Home', hash: '#faq-2' });
    };
    const navigateToContact = () => {
      router.push({ name: 'Home', hash: '#contact' });
    };

    const logout = () => {
      store.dispatch('logout');
      localStorage.removeItem('username');
      router.push('/');
    };

    return {
      username,
      isNoMarginPage,
      navigateToFaq,
      navigateToContact,
      showAuthOptions,
      logout,
    };
  },
};
</script>

<style scoped>
.header {
  margin-left: 250px;
  width: calc(100% - 250px);
  z-index: 1050;
}

.header.no-margin-left {
  margin-left: 0;
  width: 100%;
}

.auth-links {
  position: relative;
}

/* 动画效果：淡入和向上滑入 */
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}
.fade-slide-enter-from,
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(10px); /* 初始状态向下位移 */
}

.dropdown-options {
  position: absolute;
  top: 100%;
  left: 63%; /* 将位置向右移动 */
  transform: translateX(-50%);
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 5px 10px;
  white-space: nowrap;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1051;
}
</style>
