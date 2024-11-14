<template>
  <div id="app">
    <Header /> <!-- 导航栏组件 -->

    <div class="layout">
      <!-- 根据当前路由名称判断是否显示侧边栏 -->
      <Sidebar v-if="!isHomePage" /> <!-- 如果不是 Home 页面，显示侧边栏 -->

      <div class="main-content" :class="{ 'no-sidebar': isHomePage }">
        <router-view /> <!-- 用于显示页面内容 -->
      </div>
    </div>

    <Footer /> <!-- 页脚组件 -->
  </div>
</template>

<script>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import Sidebar from './components/Sidebar.vue';
import { computed } from 'vue';
import { useRoute } from 'vue-router';

export default {
  name: 'App',
  components: {
    Header,
    Footer,
    Sidebar
  },
  setup() {
    const route = useRoute();
    // 判断当前路由是否是 Home
    const isHomePage = computed(() => route.name === 'Home'|| route.name === 'Login'|| route.name === 'Register');
    return { isHomePage };
  }
};
</script>

<style>
/* Sidebar.vue */
#layoutSidenav_nav {
  width: 250px; /* 确保侧边栏宽度为固定值 */
  position: fixed;
}

/* App.vue 中的样式 */
.layout {
  display: flex;
  width: 100%;
}

.main-content {
  margin-left: 250px; /* 与侧边栏宽度一致 */
  width: calc(100% - 250px); /* 内容宽度为页面宽度减去侧边栏宽度 */
  padding-top: 100px; /* 增加顶部内边距，防止内容被顶部遮盖 */
  padding-bottom: 150px; /* 增加底部内边距，防止内容被底部遮盖 */
}

/* 当页面没有侧边栏时的样式 */
.main-content.no-sidebar {
  margin-left: 0;
  width: 100%; /* 占据全宽 */
  padding-top: 85px; /* 增加顶部内边距 */
  padding-bottom: 150px; /* 增加底部内边距 */
}



</style>
