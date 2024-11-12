<script setup>

</script>

<template>
  <header id="header" class="header d-flex align-items-center fixed-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">

      <a href="index.html" class="logo d-flex align-items-center me-auto">
        <!-- Uncomment the line below if you also wish to use an image logo -->
        <!-- <img src="assets/img/logo.png" alt=""> -->
        <h1 class="sitename">mua</h1>
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

      <!-- 判断用户是否已登录 -->
      <router-link v-if="username" class="btn-getstarted" to="/profile">{{ username }}</router-link>
      <router-link v-else class="btn-getstarted" to="/login">登录</router-link>

    </div>
  </header>
</template>
<script>
import { computed } from 'vue';
import { useStore } from 'vuex';
export default {
  name: 'Header',
  setup() {
    const store = useStore();
    const username = computed(() => store.state.username); // 获取用户名

    return {
      username,
    };
  },
};
</script>
<style scoped>

</style>