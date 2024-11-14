<template>
  <div class="entry-review-page">
    <h2>入营审核</h2>

    <!-- 学生列表表格 -->
    <table class="table">
      <thead>
      <tr>
        <th>姓名</th>
        <th>邮箱</th>
        <th>学院</th>
        <th>专业</th>
        <th>状态</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="application in pendingStudents" :key="application.id">
        <td>{{ application.user.username }}</td>
        <td>{{ application.user.email }}</td>
        <td>{{ getCollegeName(application.collegeId) }}</td>
        <td>{{ getMajorName(application.majorId) }}</td>
        <td>{{ application.status }}</td>
        <td>
          <button @click="viewDetails(application.id)">查看详情</button>
          <button @click="approveEntry(application.id)">通过</button>
          <button @click="rejectEntry(application.id)">拒绝</button>
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
  name: 'EntryReview',
  data() {
    return {
      pendingStudents: [], // 存储所有 PENDING 状态的学生
      colleges: [],        // 存储学院列表
      majors: [],          // 存储专业列表
      message: '',         // 显示提示信息
      isSuccess: false     // 成功或失败标志
    };
  },
  methods: {
    async fetchPendingStudents() {
      try {
        const response = await axios.get('/applications/pending');
        this.pendingStudents = response.data;
      } catch (error) {
        console.error('获取待审核学生列表失败', error);
      }
    },
    async fetchCollegesAndMajors() {
      try {
        const collegesResponse = await axios.get('/colleges/all');
        this.colleges = collegesResponse.data;

        const majorsResponse = await axios.get('/majors/all');
        this.majors = majorsResponse.data;
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
    getCollegeName(collegeId) {
      const college = this.colleges.find(c => c.id === collegeId);
      return college ? college.name : '未知学院';
    },
    getMajorName(majorId) {
      const major = this.majors.find(m => m.id === majorId);
      return major ? major.name : '未知专业';
    },
    viewDetails(studentId) {
      this.$router.push({ name: 'StudentDetails', params: { id: studentId } });
    },
    async approveEntry(studentId) {
      try {
        // 更新状态为 APPROVED
        await axios.patch(`/applications/${studentId}/status`, null, {
          params: { status: 'APPROVED' },
        });
        this.message = '学生已成功通过入营审核';
        this.isSuccess = true;
        this.fetchPendingStudents(); // 刷新列表
      } catch (error) {
        console.error('通过审核失败', error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    },
    async rejectEntry(studentId) {
      try {
        // 更新状态为 REJECTED
        await axios.patch(`/applications/${studentId}/status`, null, {
          params: { status: 'REJECTED' },
        });
        this.message = '学生已拒绝入营审核';
        this.isSuccess = true;
        this.fetchPendingStudents(); // 刷新列表
      } catch (error) {
        console.error('拒绝审核失败', error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    }
  },
  mounted() {
    this.fetchPendingStudents();       // 页面加载时获取待审核学生列表
    this.fetchCollegesAndMajors();      // 获取学院和专业数据
  }
};
</script>

<style scoped>
.entry-review-page {
  padding: 20px;
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
