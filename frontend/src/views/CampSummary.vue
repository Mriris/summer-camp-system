<template>
  <div id="layoutSidenav">
    <div id="layoutSidenav_content">
      <main>
        <div class="container-fluid px-4">
          <h1 class="mt-4">夏令营总结</h1>
          <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item active">学校管理员页面</li>
          </ol>
          <div class="row">
            <div class="col-xl-3 col-md-6">
              <div class="card bg-primary text-white mb-4">
                <div class="card-body">总报名人数</div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                  <span>100 人</span>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6">
              <div class="card bg-success text-white mb-4">
                <div class="card-body">优营人数</div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                  <span>75 人</span>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6">
              <div class="card bg-warning text-white mb-4">
                <div class="card-body">待审核人数</div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                  <span>15 人</span>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6">
              <div class="card bg-danger text-white mb-4">
                <div class="card-body">未优营人数</div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                  <span>10 人</span>
                </div>
              </div>
            </div>
          </div>

          <!-- 专业分布图表 -->
          <div class="row">
            <div class="col-xl-6">
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-chart-area me-1"></i>
                  各专业报名情况
                </div>
                <div class="card-body"><canvas id="myAreaChart" width="100%" height="40"></canvas></div>
              </div>
            </div>
            <div class="col-xl-6">
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-chart-bar me-1"></i>
                  各专业优营率
                </div>
                <div class="card-body"><canvas id="myBarChart" width="100%" height="40"></canvas></div>
              </div>
            </div>
          </div>

          <!-- 报名人员列表 -->
          <div class="card mb-4">
            <div class="card-header">
              <i class="fas fa-table me-1"></i>
              报名人员名单
            </div>
            <div class="card-body">
              <table id="datatablesSimple">
                <thead>
                <tr>
                  <th>姓名</th>
                  <th>专业</th>
                  <th>学院</th>
                  <th>报名日期</th>
                  <th>状态</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>张伟</td>
                  <td>计算机科学与技术</td>
                  <td>计算机学院</td>
                  <td>2024/11/01</td>
                  <td>优营</td>
                </tr>
                <tr>
                  <td>王芳</td>
                  <td>数据科学</td>
                  <td>信息工程学院</td>
                  <td>2024/11/02</td>
                  <td>待审核</td>
                </tr>
                <tr>
                  <td>李娜</td>
                  <td>软件工程</td>
                  <td>软件学院</td>
                  <td>2024/11/03</td>
                  <td>未优营</td>
                </tr>
                <tr>
                  <td>赵强</td>
                  <td>人工智能</td>
                  <td>计算机学院</td>
                  <td>2024/11/04</td>
                  <td>优营</td>
                </tr>
                <tr>
                  <td>刘洋</td>
                  <td>市场营销</td>
                  <td>商学院</td>
                  <td>2024/11/05</td>
                  <td>优营</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </main>
      <footer class="py-4 bg-light mt-auto">
        <div class="container-fluid px-4">
          <div class="d-flex align-items-center justify-content-between small">
            <div class="text-muted">mua 版权所有 &copy; 夏令营管理系统 2024</div>
            <div>
              <a href="#">隐私政策</a>
              &middot;
              <a href="#">条款和条件</a>
            </div>
          </div>
        </div>
      </footer>
    </div>
  </div>
</template>

<script>
import Sidebar from '../components/Sidebar.vue';

export default {
  name: "CampOverviewComponent",
  components: {
    Sidebar
  },
  mounted() {
    // 加载 DataTables 的脚本
    const script = document.createElement("script");
    script.src = "https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js";
    script.onload = () => {
      const table = document.getElementById('datatablesSimple');
      if (table) {
        new simpleDatatables.DataTable(table);
      }
    };
    document.head.appendChild(script);

    // 引入图表脚本
    const chartScript = document.createElement("script");
    chartScript.src = "https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js";
    chartScript.onload = () => {
      // 模拟的报名情况数据
      const ctx1 = document.getElementById('myAreaChart').getContext('2d');
      new Chart(ctx1, {
        type: 'line',
        data: {
          labels: ['计算机学院', '信息工程学院', '软件学院', '商学院'],
          datasets: [{
            label: '报名人数',
            data: [30, 20, 25, 25],
            borderColor: 'rgb(75, 192, 192)',
            fill: false
          }]
        }
      });

      const ctx2 = document.getElementById('myBarChart').getContext('2d');
      new Chart(ctx2, {
        type: 'bar',
        data: {
          labels: ['计算机学院', '信息工程学院', '软件学院', '商学院'],
          datasets: [{
            label: '优营率 (%)',
            data: [80, 70, 60, 75],
            backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 206, 86, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'],
            borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 206, 86, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)'],
            borderWidth: 1
          }]
        }
      });
    };
    document.head.appendChild(chartScript);
  }
};
</script>

<style scoped>
@import url("https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css");
@import url("https://use.fontawesome.com/releases/v6.3.0/css/all.css");

#layoutSidenav_content {
  padding: 20px;
}
</style>
