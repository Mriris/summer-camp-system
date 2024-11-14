<template>
  <div class="camp-summary-page">
    <h2>夏令营总结</h2>

    <!-- 总览统计 -->
    <div class="overview-stats">
      <div class="stat-box">
        <h3>总评选学生数</h3>
        <p>{{ totalStudents }}</p>
      </div>
      <div class="stat-box">
        <h3>已评选学生数</h3>
        <p>{{ evaluatedStudents }}</p>
      </div>
      <div class="stat-box">
        <h3>未评选学生数</h3>
        <p>{{ unEvaluatedStudents }}</p>
      </div>
    </div>

    <!-- 各专业评选情况 -->
    <div class="major-distribution">
      <h3>各专业评选情况</h3>
      <table class="table">
        <thead>
        <tr>
          <th>专业</th>
          <th>评选学生数</th>
          <th>平均分</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(data, majorName) in majorScoreData" :key="majorName">
          <td>{{ majorName }}</td>
          <td>{{ data.count }}</td>
          <td>{{ data.averageScore ? data.averageScore.toFixed(2) : '无评分' }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 各学院评分情况 -->
    <div class="college-distribution">
      <h3>各学院评分情况</h3>
      <table class="table">
        <thead>
        <tr>
          <th>学院</th>
          <th>评选学生数</th>
          <th>平均分</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(data, collegeName) in collegeScoreData" :key="collegeName">
          <td>{{ collegeName }}</td>
          <td>{{ data.count }}</td>
          <td>{{ data.averageScore ? data.averageScore.toFixed(2) : '无评分' }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'CampSummary',
  data() {
    return {
      reviewResults: [],   // 评分数据
      applications: [],    // 学生申请数据
      colleges: [],        // 学院信息
      majors: [],          // 专业信息
      departmentIdPrefix: null,   // 院系前缀
      departmentName: '',  // 院系名称
    };
  },
  computed: {
    // 计算总评选学生数
    totalStudents() {
      return this.applications.length;
    },
    // 已评选学生数
    evaluatedStudents() {
      return this.reviewResults.filter(result => result.score !== null).length;
    },
    // 未评选学生数
    unEvaluatedStudents() {
      return this.reviewResults.filter(result => result.score === null).length;
    },
    // 各专业评分情况
    majorScoreData() {
      const data = {};

      // 筛选出该学院所有的专业
      const departmentMajors = this.majors.filter((major) => {
        return major.college && major.college.id === this.departmentIdPrefix;
      });

      // 构建 `applicationId` 到 `majorId` 的映射
      const applicationToMajorMap = {};
      this.applications.forEach(application => {
        applicationToMajorMap[application.id] = application.majorId;
      });

      // 构建专业评分统计
      departmentMajors.forEach((major) => {
        const majorName = major.name;
        const relatedResults = this.reviewResults.filter(result => {
          const majorId = applicationToMajorMap[result.applicationId];
          return majorId === major.id;
        });

        const count = relatedResults.length;
        const totalScore = relatedResults.reduce((sum, result) => sum + (result.score || 0), 0);
        const averageScore = count > 0 ? totalScore / count : null;

        data[majorName] = {
          count,
          averageScore
        };
      });

      return data;
    },
    // 各学院评分情况
    collegeScoreData() {
      const data = {};

      // 构建学院评分统计
      this.colleges.forEach((college) => {
        const collegeName = college.name;
        const relatedResults = this.reviewResults.filter(result => {
          const application = this.applications.find(app => app.id === result.applicationId);
          return application && application.collegeId === college.id;
        });

        const count = relatedResults.length;
        const totalScore = relatedResults.reduce((sum, result) => sum + (result.score || 0), 0);
        const averageScore = count > 0 ? totalScore / count : null;

        data[collegeName] = {
          count,
          averageScore
        };
      });

      return data;
    },
  },
  methods: {
    // 获取评分数据
    async fetchReviewResults() {
      try {
        const response = await axios.get('/review-results/collegeId', {
          params: { collegeId: this.departmentIdPrefix }
        });
        this.reviewResults = response.data;
      } catch (error) {
        console.error('获取评分数据失败', error);
      }
    },
    // 获取所有申请信息
    async fetchApplications() {
      try {
        const response = await axios.get('/applications/list');
        this.applications = response.data;
      } catch (error) {
        console.error('获取 Application 数据失败', error);
      }
    },
    // 获取学院和专业信息
    async fetchCollegesAndMajors() {
      try {
        const collegesResponse = await axios.get('/colleges/all');
        this.colleges = collegesResponse.data;

        const majorsResponse = await axios.get('/majors/all');
        this.majors = majorsResponse.data;

        // 设置当前院系
        const department = this.colleges.find(c => c.id === this.departmentIdPrefix);
        this.departmentName = department ? department.name : '未知院系';
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
  },
  async mounted() {
    const idNumber = localStorage.getItem('idNumber');
    if (idNumber && !isNaN(parseInt(idNumber.substring(0, 2)))) {
      this.departmentIdPrefix = parseInt(idNumber.substring(0, 2));
    } else {
      console.warn("idNumber 无效，无法获取院系 ID 前缀");
      this.departmentIdPrefix = null;
    }

    await this.fetchCollegesAndMajors();  // 获取学院和专业信息
    await this.fetchApplications();       // 获取申请数据
    await this.fetchReviewResults();      // 获取评分数据
  }
};
</script>

<style scoped>
.camp-summary-page {
  padding: 20px;
}

.overview-stats {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.stat-box {
  flex: 1;
  min-width: 150px;
  background-color: #f4f4f9;
  padding: 15px;
  border-radius: 8px;
  text-align: center;
}

.major-distribution,
.college-distribution {
  margin-top: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.table th, .table td {
  padding: 8px;
  border: 1px solid #ddd;
  text-align: left;
}
</style>
