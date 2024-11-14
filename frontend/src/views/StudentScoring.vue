<template>
  <div class="student-scoring-page">
    <h2>学员评分 - {{ departmentName }}</h2>

    <!-- 筛选条件 -->
    <div class="filters">
      <label for="majorFilter">筛选专业：</label>
      <select id="majorFilter" v-model="selectedMajor">
        <option value="">全部</option>
        <option v-for="major in departmentMajors" :key="major.id" :value="major.id">
          {{ major.name }}
        </option>
      </select>
    </div>

    <!-- 学生评分表格 -->
    <table class="table">
      <thead>
      <tr>
        <th>姓名</th>
        <th>邮箱</th>
        <th>申请专业</th>
        <th>评分</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="application in filteredStudents" :key="application.id">
        <td>{{ application.user.username }}</td>
        <td>{{ application.user.email }}</td>
        <td>{{ getMajorName(application.majorId) }}</td>
        <td>
          <!-- 显示已有评分或默认输入框 -->
          <input
              type="number"
              v-model="application.score"
              :placeholder="application.score !== null ? '' : '分数'"
              min="0"
              max="100"
          />
        </td>
        <td>
          <button @click="submitScore(application.id, application.score)">提交评分</button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 提示信息 -->
    <p v-if="message" :class="{ 'text-success': isSuccess, 'text-danger': !isSuccess }">
      {{ message }}
    </p>
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'StudentScoring',
  data() {
    return {
      approvedStudents: [],
      colleges: [],
      majors: [],
      departmentIdPrefix: null,
      departmentName: '',
      selectedMajor: '',
      message: '',
      isSuccess: false
    };
  },
  computed: {
    departmentMajors() {
      if (this.departmentIdPrefix && this.majors.length) {
        return this.majors.filter((major) => major.college?.id === this.departmentIdPrefix);
      }
      return [];
    },
    filteredStudents() {
      return this.approvedStudents.filter((student) => {
        if (!student.collegeId) return false;
        const isMajorMatch = !this.selectedMajor || student.majorId === this.selectedMajor;
        return student.collegeId.toString().startsWith(this.departmentIdPrefix.toString()) && isMajorMatch;
      });
    }
  },
  methods: {
    async fetchApprovedStudents() {
      try {
        const response = await axios.get('/applications/status', {
          params: { statuses: 'APPROVED' }
        });

        console.log("获取的已通过学生数据：", response.data);  // 打印返回的学生数据

        this.approvedStudents = await Promise.all(
            response.data.map(async (student) => {
              let score = null;
              try {
                // 请求获取该 Application 的 ReviewResult
                const reviewResultResponse = await axios.get(`/review-results/application/${student.id}`);
                const reviewResult = reviewResultResponse.data;

                // 如果有 ReviewResult，则获取 score
                if (reviewResult && reviewResult.length > 0) {
                  score = reviewResult[0].score;  // 假设只需第一个评分记录
                  console.log(`学生ID: ${student.id}, 评分: ${score}`);
                }
              } catch (err) {
                console.warn(`无法获取学生ID ${student.id} 的评分数据`, err);
              }

              return {
                ...student,
                score: score
              };
            })
        );
      } catch (error) {
        console.error('获取已通过学生列表失败', error);
      }
    },
    async fetchCollegesAndMajors() {
      try {
        const collegesResponse = await axios.get('/colleges/all');
        this.colleges = collegesResponse.data;

        const majorsResponse = await axios.get('/majors/all');
        this.majors = majorsResponse.data;

        const department = this.colleges.find(
            (c) => parseInt(c.id.toString().substring(0, 2)) === this.departmentIdPrefix
        );
        this.departmentName = department ? department.name : '未知院系';
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
    getMajorName(majorId) {
      const major = this.majors.find((m) => m.id === majorId);
      return major ? major.name : '未知专业';
    },
    async submitScore(applicationId, score) {
      if (score === null || score === '') {
        this.message = '请输入有效分数';
        this.isSuccess = false;
        return;
      }
      try {
        await axios.patch(`/review-results/${applicationId}/score`, null, {
          params: { score }  // 使用 params 传递 score
        });
        // this.message = '评分已提交';
        this.isSuccess = true;
      } catch (error) {
        console.error('提交评分失败', error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    }
  },
  mounted() {
    const idNumber = localStorage.getItem('idNumber');
    if (idNumber && !isNaN(parseInt(idNumber.substring(0, 2)))) {
      this.departmentIdPrefix = parseInt(idNumber.substring(0, 2));
    } else {
      console.warn("idNumber 无效，无法获取院系 ID 前缀");
      this.departmentIdPrefix = null;
    }

    this.fetchApprovedStudents();
    this.fetchCollegesAndMajors();
  }
};
</script>

<style scoped>
.student-scoring-page {
  padding: 20px;
}

.filters {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.table th, .table td {
  padding: 8px;
  border: 1px solid #ddd;
  text-align: left;
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
