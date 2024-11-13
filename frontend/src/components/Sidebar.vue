<template>
  <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
    <div class="sb-sidenav-menu">
      <div class="nav">
        <div class="sb-sidenav-menu-heading">功能</div>

        <!-- 报名链接 -->
        <a class="nav-link" href="/Application">
          <div class="sb-nav-link-icon"><i class="fas fa-edit"></i></div>
          报名
        </a>

        <!-- 成绩查询链接 -->
        <a class="nav-link" href="/ScoreQuery">
          <div class="sb-nav-link-icon"><i class="fas fa-chart-bar"></i></div>
          成绩查询
        </a>
      </div>
    </div>

    <div class="sb-sidenav-footer">
      <div class="small">当前登录用户：</div>
      Start Bootstrap <!-- 可替换为动态用户名 -->
    </div>
  </nav>
</template>

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
</style>
