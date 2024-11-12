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
          <li><a href="#hero" class="active">主页</a></li>
          <li><a href="#about">关于我们</a></li>
          <li><a href="#services">特点</a></li>
          <li><a href="#team">团队介绍</a></li>
          <router-link to="/register">注册</router-link>
          <router-link to="/login">登录</router-link>
        </ul>
        <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
      </nav>

      <a class="btn-getstarted" href="#about">登录</a>

    </div>
  </header>
</template>
<script>
export default {
  name: 'Header'
};
</script>
<style scoped>

</style>