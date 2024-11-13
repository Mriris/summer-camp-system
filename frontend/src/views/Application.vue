<template>
  <div class="application-page">
    <h2>夏令营报名</h2>

    <!-- 显示用户基本信息 -->
    <div class="user-info">
      <p><strong>用户名:</strong> {{ userInfo.username }}</p>
      <p><strong>邮箱:</strong> {{ userInfo.email }}</p>
      <p><strong>身份证号:</strong> {{ userInfo.idNumber }}</p>
    </div>

    <!-- 报名信息表单 -->
    <form @submit.prevent="handleSubmit" class="application-form" v-if="canApply || isModifiable">
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

      <!-- 提交/修改按钮 -->
      <button :disabled="!isModifiable" type="submit" class="btn-submit">
        {{ isModifiable ? (hasApplied ? '修改报名' : '提交报名') : '不可修改' }}
      </button>

      <!-- 提交结果信息 -->
      <p v-if="message" :class="{ 'text-success': isSuccess, 'text-danger': !isSuccess }">
        {{ message }}
      </p>
    </form>

    <!-- 已报名且不可修改时显示的消息 -->
    <p v-if="hasApplied && !isModifiable">
      您的报名状态为 {{ application.status }}，无法修改。
    </p>
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
      userInfo: {}, // 用于存储用户的不可编辑信息
      hasApplied: false, // 是否已报名
      isModifiable: false, // 是否允许修改报名
      canApply: true, // 是否可以报名
    };
  },
  methods: {
    async fetchUserInfo() {
      const userId = localStorage.getItem('userId');
      try {
        const response = await axios.get(`/users/${userId}`);
        this.userInfo = response.data;
        await this.checkApplicationStatus(); // 检查用户报名状态
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
      } catch (error) {
        console.error('无法获取专业数据', error);
      }
    },
    async fetchAdvisors() {
      try {
        const response = await axios.get(`/advisors/major/${this.application.majorId}`);
        this.advisors = response.data;
      } catch (error) {
        console.error('无法获取导师数据', error);
      }
    },
    async checkApplicationStatus() {
      const userId = localStorage.getItem('userId');
      try {
        // 获取用户的报名信息
        const response = await axios.get(`/applications/user/${userId}`);
        if (response.data) {
          // 如果存在报名记录，填充表单
          this.application = response.data;
          this.hasApplied = true;
          this.isModifiable = this.application.status === 'UNPAID';
          this.canApply = false; // 禁止再次报名

          // 输出报名信息到控制台
          console.log("用户报名信息:", this.application);

          // 填充报名信息，自动显示已报名的学院、专业和导师
          await this.fetchColleges();
          await this.fetchMajors();
          await this.fetchAdvisors();
        }
      } catch (error) {
        console.error('未找到用户报名记录', error);
        this.hasApplied = false;
        this.isModifiable = true;
      }
    },
    async handleSubmit() {
      const userId = localStorage.getItem('userId');
      // 判断是否已报名，根据结果使用不同的 API 路由
      const url = `/applications/${this.hasApplied ? `update/${this.application.id}` : 'submit'}?userId=${userId}`;
      try {
        // 根据情况选择提交方式
        if (this.hasApplied) {
          await axios.patch(url, this.application);  // 使用 PATCH 进行更新
          this.message = '报名信息已修改！';
        } else {
          await axios.post(url, this.application);   // 使用 POST 进行新报名
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

.user-info, .form-group, .btn-submit {
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

.btn-submit {
  text-align: center;
  cursor: pointer;
  font-weight: bold;
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
