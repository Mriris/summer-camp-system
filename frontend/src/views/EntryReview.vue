<template>
  <div class="entry-review-page">
    <h2>入营审核 - {{ departmentName }}</h2>

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
          <!-- 如果状态为已通过，禁用“通过”和“拒绝”按钮 -->
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
      selectedMajor: '', // 当前选中的专业
      selectedStatus: '', // 当前选中的状态
      message: '',
      isSuccess: false
    };
  },
  computed: {
    departmentMajors() {
      if (this.departmentIdPrefix && this.majors.length) {
        return this.majors.filter((major) => {
          return major.college && major.college.id === this.departmentIdPrefix;
        });
      }
      return [];
    },
    filteredStudents() {
      return this.pendingStudents.filter((student) => {
        if (!student.collegeId) return false;

        const isMajorMatch = !this.selectedMajor || student.majorId === this.selectedMajor;
        const isStatusMatch = !this.selectedStatus || student.status === this.selectedStatus;

        return (
            student.collegeId.toString().startsWith(this.departmentIdPrefix.toString()) &&
            isMajorMatch &&
            isStatusMatch
        );
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
    getStatusLabel(status) {
      const statusMap = {
        PENDING: '待审核',
        APPROVED: '已通过',
        REJECTED: '已拒绝'
      };
      return statusMap[status] || status;
    },
    viewDetails(studentId) {
      this.$router.push({ name: 'StudentDetails', params: { id: studentId } });
    },
    async approveEntry(studentId) {
      try {
        const statusResponse = await axios.patch(`/applications/${studentId}/status`, null, { params: { status: 'APPROVED' } });
        const reviewResultResponse = await axios.post(`/review-results/application`, { applicationId: studentId });
        this.isSuccess = true;
        // this.message = '审核已通过，并创建评分条目';
        this.fetchPendingStudents(); // 刷新列表
      } catch (error) {
        console.error("Error during approval or review result creation:", error);
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
    } else {
      console.warn("idNumber 无效，无法获取院系 ID 前缀");
      this.departmentIdPrefix = null;
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
