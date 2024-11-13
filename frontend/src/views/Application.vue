<template>
  <div class="application-page">
    <h2>夏令营报名</h2>
    <form @submit.prevent="submitApplication">
      <!-- 选择学院 -->
      <div class="form-group">
        <label for="college">选择学院</label>
        <select v-model="application.collegeId" id="college" @change="fetchMajors">
          <option v-for="college in colleges" :key="college.id" :value="college.id">
            {{ college.name }}
          </option>
        </select>
      </div>

      <!-- 选择专业 -->
      <div class="form-group" v-if="majors.length">
        <label for="major">选择专业</label>
        <select v-model="application.majorId" id="major" @change="fetchAdvisors">
          <option v-for="major in majors" :key="major.id" :value="major.id">
            {{ major.name }}
          </option>
        </select>
      </div>

      <!-- 选择导师 -->
      <div class="form-group" v-if="advisors.length">
        <label for="advisor">选择导师（可选）</label>
        <select v-model="application.advisorId" id="advisor">
          <option value="">无</option>
          <option v-for="advisor in advisors" :key="advisor.id" :value="advisor.id">
            {{ advisor.name }}
          </option>
        </select>
      </div>

      <!-- 提交按钮 -->
      <button type="submit" class="btn-submit">提交报名</button>

      <!-- 提交结果信息 -->
      <p v-if="message" :class="{ 'text-success': isSuccess, 'text-danger': !isSuccess }">
        {{ message }}
      </p>
    </form>
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
      },
      colleges: [],
      majors: [],
      advisors: [],
      message: '',
      isSuccess: false,
    };
  },
  methods: {
    async fetchColleges() {
      try {
        const response = await axios.get('/colleges/all');  // 更新为正确的URL
        this.colleges = response.data;
      } catch (error) {
        console.error('无法获取学院数据', error);
      }
    },
    async fetchMajors() {
      this.application.majorId = null;
      this.application.advisorId = null;
      try {
        const response = await axios.get(`/majors/college/${this.application.collegeId}`);
        this.majors = response.data;
        this.advisors = [];
      } catch (error) {
        console.error('无法获取专业数据', error);
      }
    },
    async fetchAdvisors() {
      this.application.advisorId = null;
      try {
        const response = await axios.get(`/advisors/major/${this.application.majorId}`);
        this.advisors = response.data;
      } catch (error) {
        console.error('无法获取导师数据', error);
      }
    },
    async submitApplication() {
      try {
        // 从 localStorage 获取 userId
        const userId = localStorage.getItem('userId');
        const response = await axios.post(`/applications/submit?userId=${userId}`, this.application);
        this.message = '报名成功！';
        this.isSuccess = true;
      } catch (error) {
        console.error('报名失败', error);
        this.message = '报名失败，请重试。';
        this.isSuccess = false;
      }
    },
  },
  mounted() {
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

.form-group {
  margin-bottom: 15px;
  width: 100%;
  max-width: 400px;
}

.btn-submit {
  margin-top: 10px;
  padding: 10px 20px;
  width: 100%;
  max-width: 400px;
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
