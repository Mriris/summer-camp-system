<template>
  <div id="app">
    <Header />   <!-- 导航栏组件 -->
    <router-view />  <!-- 用于显示页面内容 -->
    <Footer />   <!-- 页脚组件 -->
  </div>
</template>

<script>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';

export default {
  name: 'App',
  components: {
    Header,
    Footer
  }
};
</script>

<style>
/* 在这里添加全局样式 */
</style>
