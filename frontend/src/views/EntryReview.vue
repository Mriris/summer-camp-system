<template>
  <div class="entry-review-page">
    <h2 v-if="role === '2'">入营审核 - 全校视图</h2>
    <h2 v-else>入营审核 - {{ departmentName }}</h2>

    <!-- 学院选择（仅在 role 为 2 时显示） -->
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

      <label for="statusFilter">筛选状态：</label>
      <select id="statusFilter" v-model="selectedStatus">
        <option value="">全部</option>
        <option value="PENDING">待审核</option>
        <option value="APPROVED">已通过</option>
        <option value="REJECTED">已拒绝</option>
      </select>
    </div>

    <!-- 学生列表表格 -->
    <table class="table">
      <thead>
      <tr>
        <th>姓名</th>
        <th>邮箱</th>
        <th>申请专业</th>
        <th>状态</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="application in filteredStudents" :key="application.id">
        <td>{{ application.user.username }}</td>
        <td>{{ application.user.email }}</td>
        <td>{{ getMajorName(application.majorId) }}</td>
        <td>{{ getStatusLabel(application.status) }}</td>
        <td>
          <button @click="viewDetails(application.id)">查看详情</button>
          <button @click="approveEntry(application.id)" :disabled="application.status === 'APPROVED'">通过</button>
          <button @click="rejectEntry(application.id)" :disabled="application.status === 'APPROVED'">拒绝</button>
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
      pendingStudents: [],
      colleges: [],
      majors: [],
      departmentIdPrefix: null,
      departmentName: '',
      selectedMajor: '',
      selectedStatus: '',
      selectedCollegeId: '', // 当前选中的学院 ID，仅在 role 为 2 时使用
      message: '',
      isSuccess: false,
      role: localStorage.getItem('role')
    };
  },
  computed: {
    departmentMajors() {
      return this.majors.filter(major => !this.selectedCollegeId || (major.college && major.college.id === this.selectedCollegeId));
    },
    filteredStudents() {
      return this.pendingStudents.filter(student => {
        const isMajorMatch = !this.selectedMajor || student.majorId === this.selectedMajor;
        const isStatusMatch = !this.selectedStatus || student.status === this.selectedStatus;

        // 当 selectedCollegeId 为空时，不筛选学院
        return (!this.selectedCollegeId || student.collegeId === this.selectedCollegeId) && isMajorMatch && isStatusMatch;
      });
    }
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

        if (this.role !== '2') {
          const department = this.colleges.find(c => c.id === this.departmentIdPrefix);
          this.departmentName = department ? department.name : '未知院系';
        }
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
    getMajorName(majorId) {
      const major = this.majors.find(m => m.id === majorId);
      return major ? major.name : '未知专业';
    },
    getStatusLabel(status) {
      const statusMap = { PENDING: '待审核', APPROVED: '已通过', REJECTED: '已拒绝' };
      return statusMap[status] || status;
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
      this.fetchPendingStudents();
    },
    viewDetails(studentId) {
      this.$router.push({ name: 'StudentDetails', params: { id: studentId } });
    },
    async approveEntry(studentId) {
      try {
        await axios.patch(`/applications/${studentId}/status`, null, { params: { status: 'APPROVED' } });
        await axios.post(`/review-results/application`, { applicationId: studentId });
        this.isSuccess = true;
        this.fetchPendingStudents();
      } catch (error) {
        console.error("Error during approval:", error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    },
    async rejectEntry(studentId) {
      try {
        await axios.patch(`/applications/${studentId}/status`, null, { params: { status: 'REJECTED' } });
        this.isSuccess = true;
        this.fetchPendingStudents();
      } catch (error) {
        console.error('拒绝审核失败', error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    }
  },
  mounted() {
    const idNumber = localStorage.getItem('idNumber');
    if (idNumber && !isNaN(parseInt(idNumber.substring(0, 2)))) {
      this.departmentIdPrefix = parseInt(idNumber.substring(0, 2));
    }

    this.fetchPendingStudents();
    this.fetchCollegesAndMajors();
  }
};
</script>

<style scoped>
.entry-review-page {
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
