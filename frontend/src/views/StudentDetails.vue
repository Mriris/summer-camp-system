<template>
  <div class="student-details-page">
    <h2>学生详细信息</h2>

    <!-- 显示学生基本信息 -->
    <div v-if="application && application.user" class="user-info">
      <p><strong>用户名:</strong> {{ application.user.username }}</p>
      <p><strong>邮箱:</strong> {{ application.user.email }}</p>
      <p><strong>身份证号:</strong> {{ application.user.idNumber }}</p>
    </div>

    <!-- 显示报名信息 -->
    <div v-if="application" class="readonly-application">
      <p><strong>学院:</strong> {{ selectedCollege }}</p>
      <p><strong>专业:</strong> {{ selectedMajor }}</p>
      <p><strong>导师:</strong> {{ selectedAdvisor || '无' }}</p>
      <p><strong>状态:</strong> {{ statusLabel }}</p>
      <p><strong>本科专业排名:</strong> {{ application.undergraduateRank || '未填写' }}</p>
      <p><strong>本科专业人数:</strong> {{ application.totalUndergraduateStudents || '未填写' }}</p>
      <p><strong>所获奖项:</strong> {{ application.awards || '未填写' }}</p>
      <!-- 条件渲染的证明材料下载链接 -->
      <p v-if="application.proofPdf">
        <strong>证明材料:</strong>
        <a :href="application.proofPdf" target="_blank">下载PDF</a>
      </p>
    </div>

    <!-- 操作按钮 -->
    <div class="button-group">
      <button @click="goToPrevious" class="btn-nav">上一个</button>
      <button @click="approveEntry" class="btn-action">允许入营</button>
      <button @click="rejectEntry" class="btn-action">拒绝入营</button>
      <button @click="goToNext" class="btn-nav">下一个</button>
      <button @click="goBack" class="btn-nav">返回</button>
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
  name: 'StudentDetails',
  data() {
    return {
      application: null,
      selectedCollege: '',
      selectedMajor: '',
      selectedAdvisor: '',
      message: '',
      isSuccess: false,
      studentList: [],
      currentIndex: -1,
    };
  },
  computed: {
    statusLabel() {
      const statusMap = {
        UNPAID: '未缴费',
        PENDING: '待审核',
        APPROVED: '审核通过',
        REJECTED: '拒绝入营'
      };
      return statusMap[this.application?.status] || this.application?.status;
    },
  },
  methods: {
    async fetchStudentList() {
      try {
        const response = await axios.get('/applications/pending');
        this.studentList = response.data;
        this.setCurrentIndex();
      } catch (error) {
        console.error('获取学生列表失败', error);
      }
    },
    setCurrentIndex() {
      // 更新当前学生在列表中的索引
      this.currentIndex = this.studentList.findIndex(
          (student) => student.id === parseInt(this.$route.params.id)
      );
      console.log('Current Index:', this.currentIndex);
    },
    async fetchStudentDetails() {
      const applicationId = this.$route.params.id;
      try {
        const response = await axios.get(`/applications/${applicationId}`);
        this.application = response.data;
        if (this.application.proofPdf && !this.application.proofPdf.startsWith('http')) {
          this.application.proofPdf = `http://localhost:8081/${this.application.proofPdf.replace(/^.*uploads/, 'uploads')}`;
        }
        this.setReadOnlyInfo();
      } catch (error) {
        console.error('获取学生详细信息失败', error);
      }
    },
    async setReadOnlyInfo() {
      try {
        const [collegeResponse, majorResponse, advisorResponse] = await Promise.all([
          axios.get(`/colleges/${this.application.collegeId}`),
          axios.get(`/majors/${this.application.majorId}`),
          this.application.advisorId ? axios.get(`/advisors/${this.application.advisorId}`) : Promise.resolve({ data: null })
        ]);

        this.selectedCollege = collegeResponse.data.name;
        this.selectedMajor = majorResponse.data.name;
        this.selectedAdvisor = advisorResponse.data ? advisorResponse.data.name : '';
      } catch (error) {
        console.error('获取学院、专业或导师名称失败', error);
      }
    },
    goToPrevious() {
      if (this.currentIndex > 0) {
        const previousStudentId = this.studentList[this.currentIndex - 1].id;
        this.$router.push({ name: 'StudentDetails', params: { id: previousStudentId } });
      }
    },
    goToNext() {
      if (this.currentIndex < this.studentList.length - 1) {
        const nextStudentId = this.studentList[this.currentIndex + 1].id;
        this.$router.push({ name: 'StudentDetails', params: { id: nextStudentId } });
      }
    },
    goBack() {
      this.$router.push({ name: 'EntryReview' });
    },
    async approveEntry() {
      try {
        await axios.patch(`/applications/${this.application.id}/status`, null, {
          params: { status: 'APPROVED' },
        });
        this.message = '学生已允许入营';
        this.isSuccess = true;
        this.fetchStudentDetails();
      } catch (error) {
        console.error('允许入营失败', error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    },
    async rejectEntry() {
      try {
        await axios.patch(`/applications/${this.application.id}/status`, null, {
          params: { status: 'REJECTED' },
        });
        this.message = '学生已拒绝入营';
        this.isSuccess = true;
        this.fetchStudentDetails();
      } catch (error) {
        console.error('拒绝入营失败', error);
        this.message = '操作失败，请重试';
        this.isSuccess = false;
      }
    },
  },
  watch: {
    '$route.params.id': {
      handler() {
        this.setCurrentIndex();
        this.fetchStudentDetails();
      },
      immediate: true,
    },
  },
  async mounted() {
    await this.fetchStudentList();
  },
};


</script>



<style scoped>
.student-details-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  text-align: center;
}

.user-info, .readonly-application, .no-edit-warning {
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  width: 100%;
  max-width: 400px;
  margin-bottom: 15px;
  text-align: left;
  box-sizing: border-box;
  border: 1px solid #ddd;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  width: 100%;
  max-width: 400px;
}

.btn-nav, .btn-action {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-nav {
  background-color: #007bff;
  color: white;
}

.btn-action {
  background-color: #28a745;
  color: white;
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
