<template>
  <div class="award-selection-page">
    <h2>优秀营员评选</h2>

    <!-- 筛选条件 -->
    <div class="filters">
      <label for="collegeFilter">筛选学院：</label>
      <select id="collegeFilter" v-model="selectedCollege">
        <option value="">全部</option>
        <option v-for="college in colleges" :key="college.id" :value="college.id">
          {{ college.name }}
        </option>
      </select>

      <label for="majorFilter">筛选专业：</label>
      <select id="majorFilter" v-model="selectedMajor">
        <option value="">全部</option>
        <option v-for="major in filteredMajors" :key="major.id" :value="major.id">
          {{ major.name }}
        </option>
      </select>
    </div>

    <!-- 学生列表表格 -->
    <table class="table">
      <thead>
      <tr>
        <th>姓名</th>
        <th>学院</th>
        <th>专业</th>
        <th>分数</th>
        <th>评选状态</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="student in filteredStudents" :key="student.id">
        <td>{{ student.username }}</td>
        <td>{{ getCollegeName(student.collegeId) }}</td>
        <td>{{ getMajorName(student.majorId) }}</td>
        <td>{{ student.score }}</td> <!-- 显示分数 -->
        <td>{{ getGradeLabel(student.grade) }}</td>
        <td>
          <button @click="awardExcellent(student.id)" :disabled="student.grade === 'EXCELLENT'">
            优秀
          </button>
          <button @click="markPass(student.id)" :disabled="student.grade === 'PASS'">
            及格
          </button>
          <button @click="markFail(student.id)" :disabled="student.grade === 'FAIL'">
            不及格
          </button>
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
  name: 'AwardSelection',
  data() {
    return {
      studentsForSelection: [],
      colleges: [],
      majors: [],
      departmentName: '',
      selectedCollege: '',
      selectedMajor: '',
      message: '',
      isSuccess: false,
    };
  },
  computed: {
    filteredMajors() {
      console.log("计算筛选后的专业列表...");  // 输出过滤过程
      if (this.selectedCollege) {
        return this.majors.filter((major) => major.college && major.college.id === this.selectedCollege);
      }
      return this.majors;
    },
    filteredStudents() {
      console.log("计算筛选后的学生列表...");  // 输出筛选过程
      return this.studentsForSelection.filter((student) => {
        const isCollegeMatch = !this.selectedCollege || student.collegeId === this.selectedCollege;
        const isMajorMatch = !this.selectedMajor || student.majorId === this.selectedMajor;

        return isCollegeMatch && isMajorMatch;
      });
    },
  },
  methods: {
    async fetchStudentsForSelection() {
      console.log("开始获取学生评选记录...");
      try {
        const response = await axios.get('/review-results/list');
        console.log("获取到学生评选记录:", response.data);

        // 遍历每个评选记录，使用 applicationId 获取用户信息
        for (let item of response.data) {
          const applicationResponse = await axios.get(`/applications/${item.applicationId}`);
          console.log("获取到的 application:", applicationResponse.data);

          const application = applicationResponse.data;

          // 从 application 中提取 collegeId 和 majorId
          item.collegeId = application.collegeId || null;  // 提取 collegeId
          item.majorId = application.majorId || null;      // 提取 majorId
          item.score = item.score || 0;                    // 确保分数存在

          // 从 application 中获取 user 对象的 username 和 email
          if (application && application.user) {
            item.username = application.user.username || '';
          }
        }

        // 更新学生列表
        this.studentsForSelection = response.data;
        console.log("最终的学生列表:", this.studentsForSelection);
      } catch (error) {
        console.error('获取待评选学生列表失败', error);
      }
    },
    async fetchCollegesAndMajors() {
      console.log("开始获取学院和专业信息...");
      try {
        const collegesResponse = await axios.get('/colleges/all');
        console.log("获取到的学院信息:", collegesResponse.data);
        this.colleges = collegesResponse.data;

        const majorsResponse = await axios.get('/majors/all');
        console.log("获取到的专业信息:", majorsResponse.data);
        this.majors = majorsResponse.data;
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
    getCollegeName(collegeId) {
      const college = this.colleges.find((c) => c.id === collegeId);
      console.log(`查找学院：${collegeId}`, college); // 调试信息
      return college ? college.name : '未知学院';
    },
    getMajorName(majorId) {
      const major = this.majors.find((m) => m.id === majorId);
      console.log(`查找专业：${majorId}`, major); // 调试信息
      return major ? major.name : '未知专业';
    },
    getGradeLabel(grade) {
      console.log("获取评分标签:", grade);  // 输出 grade 相关信息
      const gradeMap = {
        EXCELLENT: '优秀',
        PASS: '通过',
        FAIL: '未通过',
      };
      return gradeMap[grade] || grade;
    },
    async awardExcellent(studentId) {
      console.log("开始评为优秀, 学生ID:", studentId);
      try {
        await axios.patch(`/review-results/${studentId}/grade`, null, {
          params: {grade: 'EXCELLENT'}
        });
        // this.message = '评为优秀成功';
        this.isSuccess = true;
        this.fetchStudentsForSelection(); // 刷新列表
      } catch (error) {
        console.error("评为优秀失败", error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    },
    async markPass(studentId) {
      console.log("开始评为及格, 学生ID:", studentId);
      try {
        await axios.patch(`/review-results/${studentId}/grade`, null, {
          params: {grade: 'PASS'}
        });
        // this.message = '评为及格成功';
        this.isSuccess = true;
        this.fetchStudentsForSelection(); // 刷新列表
      } catch (error) {
        console.error("评为及格失败", error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    },
    async markFail(studentId) {
      console.log("开始评为不及格, 学生ID:", studentId);
      try {
        await axios.patch(`/review-results/${studentId}/grade`, null, {
          params: {grade: 'FAIL'}
        });
        // this.message = '评为不及格成功';
        this.isSuccess = true;
        this.fetchStudentsForSelection(); // 刷新列表
      } catch (error) {
        console.error("评为不及格失败", error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    },
  },
  mounted() {
    console.log("页面加载完成，开始获取学生和学院信息...");
    this.fetchStudentsForSelection();
    this.fetchCollegesAndMajors();
  },
};
</script>


<style scoped>
.award-selection-page {
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
