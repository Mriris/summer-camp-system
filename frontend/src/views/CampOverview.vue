<template>
  <div class="camp-overview-page">
    <h2>本学院夏令营概览 - {{ departmentName }}</h2>

    <!-- 总览统计 -->
    <div class="overview-stats">
      <div class="stat-box">
        <h3>总评分条目</h3>
        <p>{{ totalReviewResults }}</p>
      </div>
      <div class="stat-box">
        <h3>已评分条目</h3>
        <p>{{ scoredResults }}</p>
      </div>
      <div class="stat-box">
        <h3>待评分条目</h3>
        <p>{{ unscoredResults }}</p>
      </div>
    </div>

    <!-- 各专业评分分布 -->
    <div class="major-distribution">
      <h3>各专业评分情况</h3>
      <table class="table">
        <thead>
        <tr>
          <th>专业</th>
          <th>评分条目</th>
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
  </div>
</template>

<script>
import axios from '../axiosInstance';

export default {
  name: 'CampOverview',
  data() {
    return {
      reviewResults: [],
      applications: [], // 新增用于存储 Application 数据
      colleges: [],
      majors: [],
      departmentIdPrefix: null,
      departmentName: '',
    };
  },
  computed: {
    totalReviewResults() {
      console.log("计算总评分条目，总数为：", this.reviewResults.length);
      return this.reviewResults.length;
    },
    scoredResults() {
      const scoredCount = this.reviewResults.filter(result => result.score !== null).length;
      console.log("已评分条目数为：", scoredCount);
      return scoredCount;
    },
    unscoredResults() {
      const unscoredCount = this.reviewResults.filter(result => result.score === null).length;
      console.log("待评分条目数为：", unscoredCount);
      return unscoredCount;
    },
    majorScoreData() {
      console.log("开始计算各专业评分情况");
      const data = {};

      // 筛选出该学院所有的专业
      const departmentMajors = this.majors.filter((major) => {
        const isDepartmentMajor = major.college && major.college.id === this.departmentIdPrefix;
        console.log(`专业 ${major.name} 是否属于该院系:`, isDepartmentMajor);
        return isDepartmentMajor;
      });

      // 构建 `applicationId` 到 `majorId` 的映射
      const applicationToMajorMap = {};
      this.applications.forEach(application => {
        applicationToMajorMap[application.id] = application.majorId;
      });
      console.log("Application 到 Major 的映射:", applicationToMajorMap);

      // 构建专业评分统计
      departmentMajors.forEach((major) => {
        const majorName = major.name;
        const relatedResults = this.reviewResults.filter(result => {
          const majorId = applicationToMajorMap[result.applicationId];
          return majorId === major.id;
        });

        console.log(`专业 ${majorName} 关联的评分条目数:`, relatedResults.length);

        // 统计该专业的评分数据
        const count = relatedResults.length;
        const totalScore = relatedResults.reduce((sum, result) => sum + (result.score || 0), 0);
        const averageScore = count > 0 ? totalScore / count : null;

        data[majorName] = {
          count,
          averageScore
        };
        console.log(`专业 ${majorName} 的评分条目总数: ${count}, 平均分: ${averageScore}`);
      });

      console.log("各专业评分情况计算完成:", data);
      return data;
    }
  },
  methods: {
    async fetchReviewResults() {
      try {
        console.log("开始获取评分数据...");
        const response = await axios.get(`/review-results/collegeId`, {
          params: { collegeId: this.departmentIdPrefix }
        });
        this.reviewResults = response.data;
        console.log("评分数据获取成功:", this.reviewResults);
      } catch (error) {
        console.error('获取评分数据失败', error);
      }
    },
    async fetchApplications() {
      try {
        console.log("开始获取 Application 数据...");
        const response = await axios.get('/applications/list');
        this.applications = response.data;
        console.log("Application 数据获取成功:", this.applications);
      } catch (error) {
        console.error('获取 Application 数据失败', error);
      }
    },
    async fetchCollegesAndMajors() {
      try {
        console.log("开始获取学院和专业信息...");
        const collegesResponse = await axios.get('/colleges/all');
        this.colleges = collegesResponse.data;
        console.log("学院信息获取成功:", this.colleges);

        const majorsResponse = await axios.get('/majors/all');
        this.majors = majorsResponse.data;
        console.log("专业信息获取成功:", this.majors);

        // 找到当前院系
        const department = this.colleges.find(
            (c) => c.id === this.departmentIdPrefix
        );
        this.departmentName = department ? department.name : '未知院系';
        console.log("当前院系名称:", this.departmentName);
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
  },
  async mounted() {
    const idNumber = localStorage.getItem('idNumber');
    console.log("从 localStorage 获取的 idNumber:", idNumber);

    if (idNumber && !isNaN(parseInt(idNumber.substring(0, 2)))) {
      this.departmentIdPrefix = parseInt(idNumber.substring(0, 2));
      console.log("解析到的院系 ID 前缀:", this.departmentIdPrefix);
    } else {
      console.warn("idNumber 无效，无法获取院系 ID 前缀");
      this.departmentIdPrefix = null;
    }

    await this.fetchCollegesAndMajors();  // 确保专业信息已加载
    await this.fetchApplications();       // 获取 Application 数据以建立映射
    await this.fetchReviewResults();      // 然后获取评分数据
  }
};
</script>



<style scoped>
.camp-overview-page {
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

.major-distribution {
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
