<template>
  <div class="student-scoring-page">
    <h2 v-if="role === '2'">学员评分 - 全校视图</h2>
    <h2 v-else>学员评分 - {{ departmentName }}</h2>

    <!-- 学院选择器（仅在 role 为 2 时显示） -->
    <div v-if="role === '2'" class="college-selector">
      <label for="collegeSelect">选择学院：</label>
      <select id="collegeSelect" v-model="selectedCollegeId" @change="selectCollege">
        <option value="">请选择学院</option>
        <option v-for="college in colleges" :key="college.id" :value="college.id">
          {{ college.name }}
        </option>
      </select>
    </div>

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
      selectedCollegeId: '', // 当前选中的学院 ID，仅在 role 为 2 时使用
      message: '',
      isSuccess: false,
      role: localStorage.getItem('role') // 获取角色信息
    };
  },
  computed: {
    departmentMajors() {
      return this.majors.filter(major => !this.selectedCollegeId || major.college?.id === this.selectedCollegeId);
    },
    filteredStudents() {
      return this.approvedStudents.filter(student => {
        const isMajorMatch = !this.selectedMajor || student.majorId === this.selectedMajor;
        return (!this.selectedCollegeId || student.collegeId === this.selectedCollegeId) && isMajorMatch;
      });
    }
  },
  methods: {
    async fetchApprovedStudents() {
      try {
        const response = await axios.get('/applications/status', {
          params: { statuses: 'APPROVED' }
        });
        this.approvedStudents = await Promise.all(
            response.data.map(async (student) => {
              let score = null;
              try {
                const reviewResultResponse = await axios.get(`/review-results/application/${student.id}`);
                const reviewResult = reviewResultResponse.data;
                if (reviewResult && reviewResult.length > 0) {
                  score = reviewResult[0].score;
                }
              } catch (err) {
                console.warn(`无法获取学生ID ${student.id} 的评分数据`, err);
              }
              return { ...student, score: score };
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

        if (this.role !== '2') {
          const department = this.colleges.find(c => c.id === this.departmentIdPrefix);
          this.departmentName = department ? department.name : '未知院系';
        }
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
    selectCollege() {
      if (this.selectedCollegeId) {
        const selectedCollege = this.colleges.find(c => c.id === parseInt(this.selectedCollegeId));
        this.departmentName = selectedCollege ? selectedCollege.name : '选择的学院';
        this.departmentIdPrefix = parseInt(this.selectedCollegeId);
      } else {
        this.departmentName = '全校视图';
        this.departmentIdPrefix = null;
      }
      this.fetchApprovedStudents();
    },
    getMajorName(majorId) {
      const major = this.majors.find(m => m.id === majorId);
      return major ? major.name : '未知专业';
    },
    async submitScore(applicationId, score) {
      if (score === null || score === '') {
        this.message = '请输入有效分数';
        this.isSuccess = false;
        return;
      }
      try {
        await axios.patch(`/review-results/${applicationId}/score`, null, {params: {score}});
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

.college-selector {
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
