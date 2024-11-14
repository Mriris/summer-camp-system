<template>
  <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
    <div class="sb-sidenav-menu">
      <div class="nav">
        <div class="sb-sidenav-menu-heading">功能</div>

        <!-- 学员(0)的侧边栏内容 -->
        <template v-if="role === '0'">
          <a class="nav-link" href="/Application">
            <div class="sb-nav-link-icon"><i class="fas fa-edit"></i></div>
            报名
          </a>
          <a class="nav-link" href="/ScoreQuery">
            <div class="sb-nav-link-icon"><i class="fas fa-chart-bar"></i></div>
            成绩查询
          </a>
        </template>

        <!-- 院系(1)的侧边栏内容 -->
        <template v-else-if="role === '1'">
          <a class="nav-link" href="/EntryReview">
            <div class="sb-nav-link-icon"><i class="fas fa-check-circle"></i></div>
            入营审核
          </a>
          <a class="nav-link" href="/StudentScoring">
            <div class="sb-nav-link-icon"><i class="fas fa-star"></i></div>
            学员评分
          </a>
          <a class="nav-link" href="/CampOverview">
            <div class="sb-nav-link-icon"><i class="fas fa-info-circle"></i></div>
            学院情况
          </a>
        </template>

        <!-- 学校(2)的侧边栏内容，包括院系内容，添加分隔线 -->
        <template v-else-if="role === '2'">
          <!-- 院系功能 -->
          <a class="nav-link" href="/EntryReview">
            <div class="sb-nav-link-icon"><i class="fas fa-check-circle"></i></div>
            入营审核
          </a>
          <a class="nav-link" href="/StudentScoring">
            <div class="sb-nav-link-icon"><i class="fas fa-star"></i></div>
            学员评分
          </a>

          <!-- 分隔线 -->
          <hr class="sidebar-divider">

          <!-- 学校功能 -->
          <a class="nav-link" href="/AwardSelection">
            <div class="sb-nav-link-icon"><i class="fas fa-graduation-cap"></i></div>
            优营评选
          </a>
          <a class="nav-link" href="/CampSummary">
            <div class="sb-nav-link-icon"><i class="fas fa-file-alt"></i></div>
            夏令营总结
          </a>
        </template>
      </div>
    </div>

    <div class="sb-sidenav-footer">
      <div class="small">当前登录用户：</div>
      {{ username }}
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      role: localStorage.getItem("role"), // 获取角色
      username: localStorage.getItem("username") // 获取用户名
    };
  }
};
</script>

<style scoped>
.sb-sidenav {
  width: 250px;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  overflow-y: auto;
  z-index: 1000;
}

.sidebar-divider {
  border-top: 1px solid rgba(255, 255, 255, 0.3);
  margin: 10px 0;
}
</style>
