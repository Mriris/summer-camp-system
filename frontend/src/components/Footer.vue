<template>
  <footer :class="['footer', { 'no-padding-left': isNoPaddingPage }]">
    <div class="container footer-top">
      <div class="row gy-4">
        <div class="col-lg-4 col-md-6 footer-about">
          <a href="/" class="d-flex align-items-center">
            <span class="sitename">mua</span>
          </a>
          <div class="footer-contact pt-3">
            <p>大连海事大学</p>
            <p>中国，辽宁省大连市</p>
            <p class="mt-3"><strong>电话:</strong> <span>15554657267</span></p>
            <p><strong>Email:</strong> <span>mua@dlmu.edu.cn</span></p>
          </div>
        </div>

        <div class="col-lg-2 col-md-3 footer-links">
          <h4>快速链接</h4>
          <ul>
            <li><i class="bi bi-chevron-right"></i> <a href="#">主页</a></li>
            <li><i class="bi bi-chevron-right"></i> <a href="#">关于我们</a></li>
            <li><i class="bi bi-chevron-right"></i> <a href="#">优势</a></li>
            <li><i class="bi bi-chevron-right"></i> <a href="#">团队介绍</a></li>
          </ul>
        </div>

        <div class="col-lg-2 col-md-3 footer-links">
          <h4>我们的服务</h4>
          <ul>
            <li><i class="bi bi-chevron-right"></i> <a href="#">报名管理</a></li>
            <li><i class="bi bi-chevron-right"></i> <a href="#">活动安排</a></li>
            <li><i class="bi bi-chevron-right"></i> <a href="#">审核流程</a></li>
            <li><i class="bi bi-chevron-right"></i> <a href="#">信息发布</a></li>
          </ul>
        </div>

        <div class="col-lg-4 col-md-12">
          <h4>关注我们</h4>
          <p>可以在GitHub了解最新动态</p>
        </div>
      </div>
    </div>

    <div class="container copyright text-center mt-4">
      <p>© <span>版权信息</span> <strong class="px-1 sitename">mua.</strong> <span>保留所有权利</span></p>
      <div class="credits">由 mua 团队制作</div>
    </div>
  </footer>
</template>

<script>
import { computed } from 'vue';
import { useRoute } from 'vue-router';

export default {
  name: 'Footer',
  setup() {
    const route = useRoute();

    // 判断是否在没有左边空隙的页面
    const isNoPaddingPage = computed(() => {
      return ['Home', 'Login', 'Register'].includes(route.name);
    });

    return {
      isNoPaddingPage,
    };
  },
};
</script>

<style scoped>
.footer {
  padding-left: 250px; /* 默认左侧空隙 */
}

.footer.no-padding-left {
  padding-left: 0; /* 无左侧空隙 */
}

@media (max-width: 991.98px) {
  .footer {
    padding-left: 0;
  }
}
</style>
