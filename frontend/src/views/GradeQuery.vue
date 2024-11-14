<template>
  <div class="grade-query-page">
    <h2>我的评分查询</h2>

    <div v-if="grade">
      <p :class="gradeClass"><strong></strong> {{ gradeTranslated }}</p>
    </div>
    <div v-else>
      <p>暂无评分结果</p>
    </div>

    <!-- 提示信息 -->
    <p v-if="message" :class="{ 'text-success': isSuccess, 'text-danger': !isSuccess }">
      {{ message }}
    </p>
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'GradeQuery',
  data() {
    return {
      grade: null, // 原始 grade 值
      message: '',
      isSuccess: false
    };
  },
  computed: {
    gradeTranslated() {
      // 转换 grade 为中文
      const gradeMap = {
        EXCELLENT: '优营',
        PASS: '及格',
        FAIL: '不及格'
      };
      return gradeMap[this.grade] || '未知等级';
    },
    gradeClass() {
      // 根据 grade 设置不同的类名
      const classMap = {
        EXCELLENT: 'text-excellent',
        PASS: 'text-pass',
        FAIL: 'text-fail'
      };
      return classMap[this.grade] || '';
    }
  },
  methods: {
    async fetchGrade() {
      try {
        const userId = localStorage.getItem('userId'); // 获取 userId
        if (!userId) {
          this.message = '无法获取用户 ID，请登录后重试';
          this.isSuccess = false;
          return;
        }

        // 获取与 userId 关联的 application
        const applicationResponse = await axios.get(`/applications/user/${userId}`);
        const application = applicationResponse.data;

        if (!application || !application.id) {
          this.message = '未找到对应的申请记录';
          this.isSuccess = false;
          return;
        }

        const applicationId = application.id; // 获取 applicationId

        // 根据 applicationId 获取 reviewResult
        const reviewResultResponse = await axios.get(`/review-results/application/${applicationId}`);
        const reviewResult = reviewResultResponse.data;

        if (reviewResult && reviewResult.length > 0) {
          this.grade = reviewResult[0].grade; // 假设取第一个 reviewResult 的 grade
          this.isSuccess = true;
        } else {
          this.message = '暂无评分结果';
          this.isSuccess = false;
        }
      } catch (error) {
        console.error('获取评分数据失败', error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    }
  },
  mounted() {
    this.fetchGrade(); // 页面加载时自动查询评分
  }
};
</script>

<style scoped>
.grade-query-page {
  padding: 20px;
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}

/* 设置评分等级的颜色 */
.text-excellent {
  color: green;
  font-weight: bold;
  font-size: 1.2em;
}

.text-pass {
  color: blue;
  font-weight: bold;
  font-size: 1.2em;
}

.text-fail {
  color: red;
  font-weight: bold;
  font-size: 1.2em;
}
</style>
