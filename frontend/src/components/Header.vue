<template>
  <header :class="['header', { 'no-margin-left': isNoMarginPage }]" class="d-flex align-items-center fixed-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">
      <a href="/" class="logo d-flex align-items-center me-auto">
        <h1 class="sitename">夏令营</h1>
      </a>

      <nav id="navmenu" class="navmenu">
        <ul>
          <li><router-link to="/" exact-active-class="active">主页</router-link></li>
          <li><a href="#about">关于我们</a></li>
          <li><a href="#services">特点</a></li>
          <li><a href="#team">团队介绍</a></li>
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
import { useRoute } from 'vue-router';

export default {
  name: 'Header',
  setup() {
    const store = useStore();
    const route = useRoute();

    // 计算属性动态获取用户名
    const username = computed(() => store.state.username);

    // 判断是否在需要无左边空隙的页面
    const isNoMarginPage = computed(() => {
      return ['Home', 'Login', 'Register'].includes(route.name);
    });

    return {
      username,
      isNoMarginPage,
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
