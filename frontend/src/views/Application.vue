<template>
  <div class="application-page">
    <h2>夏令营报名</h2>

    <!-- 显示用户基本信息 -->
    <div class="user-info">
      <p><strong>用户名:</strong> {{ userInfo.username }}</p>
      <p><strong>邮箱:</strong> {{ userInfo.email }}</p>
      <p><strong>身份证号:</strong> {{ userInfo.idNumber }}</p>
    </div>

    <!-- 报名信息表单或只读信息 -->
    <form @submit.prevent="handleSubmit" class="application-form" v-if="isModifiable || !hasApplied">
      <!-- 选择学院 -->
      <div class="form-group">
        <label for="college">选择学院</label>
        <select v-model="application.collegeId" id="college" @change="fetchMajors" :disabled="!isModifiable">
          <option v-for="college in colleges" :key="college.id" :value="college.id">
            {{ college.name }}
          </option>
        </select>
      </div>

      <!-- 选择专业 -->
      <div class="form-group" v-if="majors.length">
        <label for="major">选择专业</label>
        <select v-model="application.majorId" id="major" @change="fetchAdvisors" :disabled="!isModifiable">
          <option v-for="major in majors" :key="major.id" :value="major.id">
            {{ major.name }}
          </option>
        </select>
      </div>

      <!-- 选择导师 -->
      <div class="form-group" v-if="advisors.length">
        <label for="advisor">选择导师（可选）</label>
        <select v-model="application.advisorId" id="advisor" :disabled="!isModifiable">
          <option value="">无</option>
          <option v-for="advisor in advisors" :key="advisor.id" :value="advisor.id">
            {{ advisor.name }}
          </option>
        </select>
      </div>

      <!-- 提交或修改按钮 -->
      <button :disabled="!isModifiable" type="submit" class="btn-submit">
        {{ isModifiable ? (hasApplied ? '修改报名' : '提交报名') : '不可修改' }}
      </button>

      <!-- 提交结果信息 -->
      <p v-if="message" :class="{ 'text-success': isSuccess, 'text-danger': !isSuccess }">
        {{ message }}
      </p>
    </form>

    <!-- 已报名且不可修改时显示的只读信息 -->
    <div v-else>
      <div class="readonly-application">
        <p><strong>学院:</strong> {{ selectedCollege }}</p>
        <p><strong>专业:</strong> {{ selectedMajor }}</p>
        <p><strong>导师:</strong> {{ selectedAdvisor || '无' }}</p>
        <p><strong>状态:</strong> {{ statusLabel }}</p>
      </div>
      <div class="no-edit-warning">
        <h3>报名信息无法修改</h3>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'Application',
  data() {
    return {
      application: {
        collegeId: null,
        majorId: null,
        advisorId: null,
        status: 'UNPAID',
      },
      colleges: [],
      majors: [],
      advisors: [],
      message: '',
      isSuccess: false,
      userInfo: {},
      hasApplied: false,
      isModifiable: false,
      canApply: true,
      selectedCollege: '',
      selectedMajor: '',
      selectedAdvisor: '',
    };
  },
  computed: {
    statusLabel() {
      switch (this.application.status) {
        case 'UNPAID':
          return '未缴费';
        case 'PENDING':
          return '待审核';
        case 'APPROVED':
          return '审核通过';
        case 'REJECTED':
          return '拒绝入营';
        default:
          return this.application.status;
      }
    },
  },
  methods: {
    async fetchUserInfo() {
      const userId = localStorage.getItem('userId');
      try {
        const response = await axios.get(`/users/${userId}`);
        this.userInfo = response.data;
        await this.checkApplicationStatus();
      } catch (error) {
        console.error('无法获取用户信息', error);
      }
    },
    async fetchColleges() {
      try {
        const response = await axios.get('/colleges/all');
        this.colleges = response.data;
      } catch (error) {
        console.error('无法获取学院数据', error);
      }
    },
    async fetchMajors() {
      try {
        const response = await axios.get(`/majors/college/${this.application.collegeId}`);
        this.majors = response.data;
        this.advisors = [];
        this.setReadOnlyInfo();
      } catch (error) {
        console.error('无法获取专业数据', error);
      }
    },
    async fetchAdvisors() {
      try {
        const response = await axios.get(`/advisors/major/${this.application.majorId}`);
        this.advisors = response.data;
        this.setReadOnlyInfo();
      } catch (error) {
        console.error('无法获取导师数据', error);
      }
    },
    async checkApplicationStatus() {
      const userId = localStorage.getItem('userId');
      try {
        const response = await axios.get(`/applications/user/${userId}`);
        if (response.data) {
          this.application = response.data;
          this.hasApplied = true;
          this.isModifiable = this.application.status === 'UNPAID';
          this.canApply = false;
          await this.fetchColleges();
          await this.fetchMajors();
          await this.fetchAdvisors();
          this.setReadOnlyInfo();
        }
      } catch (error) {
        console.error('未找到用户报名记录', error);
        this.hasApplied = false;
        this.isModifiable = true;
      }
    },
    async handleSubmit() {
      const userId = localStorage.getItem('userId');
      const url = `/applications/${this.hasApplied ? `update/${this.application.id}` : 'submit'}?userId=${userId}`;
      try {
        if (this.hasApplied) {
          await axios.patch(url, this.application);
          this.message = '报名信息已修改！';
        } else {
          await axios.post(url, this.application);
          this.message = '报名成功！';
        }
        this.isSuccess = true;
        this.isModifiable = this.application.status === 'UNPAID';
      } catch (error) {
        console.error('操作失败', error);
        this.message = '操作失败，请重试。';
        this.isSuccess = false;
      }
    },
    setReadOnlyInfo() {
      this.selectedCollege = this.colleges.find(college => college.id === this.application.collegeId)?.name || '';
      this.selectedMajor = this.majors.find(major => major.id === this.application.majorId)?.name || '';
      this.selectedAdvisor = this.advisors.find(advisor => advisor.id === this.application.advisorId)?.name || '';
    },
  },
  mounted() {
    this.fetchUserInfo();
    this.fetchColleges();
  },
};
</script>

<style scoped>
.application-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 20px;
  text-align: center;
}

.user-info, .form-group, .btn-submit, .readonly-application, .no-edit-warning {
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

.no-edit-warning {
  text-align: center;
  font-weight: bold;
  color: red;
  text-align: center;
}
.no-edit-warning h3 {
  color: red;
}
</style>
