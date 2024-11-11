<template>
  <div id="app">
    <Header /> <!-- 导航栏组件 -->
    <div class="main-content">
      <router-view /> <!-- 用于显示页面内容 -->
    </div>
    <Footer /> <!-- 页脚组件 -->
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
/* 设置 main-content 的顶部内边距，确保内容不会被 Header 遮挡 */
.main-content {
  padding-top: 80px; /* 根据 Header 的高度调整 */
}
</style>
