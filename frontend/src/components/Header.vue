<template>
  <header :class="['header', { 'no-margin-left': isNoMarginPage }]" class="d-flex align-items-center fixed-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">
      <a href="/" class="logo d-flex align-items-center me-auto">
        <h1 class="sitename">夏令营</h1>
      </a>

      <nav id="navmenu" class="navmenu">
        <ul>
          <li><router-link to="/" exact-active-class="active">主页</router-link></li>
          <!-- 修改 FAQ 链接为在点击时跳转到 Home.vue 的 #faq 区域 -->
          <li><a href="#faq-2" @click.prevent="navigateToFaq">FAQ</a></li>
          <li><a href="#contact" @click.prevent="navigateToContact">联系我们</a></li>
        </ul>
        <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
      </nav>

      <!-- 判断用户是否已登录，已登录时显示用户名并链接到个人主页 -->
      <router-link v-if="username" class="btn-getstarted" to="/profile">{{ username }}</router-link>
      <router-link v-else class="btn-getstarted" to="/login">登录</router-link>
    </div>
  </header>
</template>

<script>
import { computed } from 'vue';
import { useStore } from 'vuex';
import { useRoute, useRouter } from 'vue-router';

export default {
  name: 'Header',
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();

    // 计算属性动态获取用户名
    const username = computed(() => store.state.username);

    // 判断是否在需要无左边空隙的页面
    const isNoMarginPage = computed(() => {
      return ['Home', 'Login', 'Register'].includes(route.name);
    });

    // 跳转到 Home.vue 的区域
    const navigateToFaq = () => {
      router.push({ name: 'Home', hash: '#faq-2'});
    };
    const navigateToContact = () => {
      router.push({ name: 'Home', hash: '#contact'});
    };

    return {
      username,
      isNoMarginPage,
      navigateToFaq,
      navigateToContact,  // 导出这个方法
    };

  },
};
</script>

<style scoped>
.header {
  margin-left: 250px; /* 偏移出侧边栏的宽度 */
  width: calc(100% - 250px); /* 确保 header 充满可见宽度 */
  z-index: 1050; /* 使 header 层级高于侧边栏 */
}

.header.no-margin-left {
  margin-left: 0; /* 移除左边空隙 */
  width: 100%; /* 充满整个宽度 */
}
</style>
