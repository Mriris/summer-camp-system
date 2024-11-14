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
      colleges: [],
      majors: [],
      departmentIdPrefix: null,
      departmentName: '',
    };
  },
  computed: {
    totalReviewResults() {
      console.log("总评分条目计算中，总数为：", this.reviewResults.length); // 日志
      return this.reviewResults.length;
    },
    scoredResults() {
      const scoredCount = this.reviewResults.filter(result => result.score !== null).length;
      console.log("已评分条目数为：", scoredCount); // 日志
      return scoredCount;
    },
    unscoredResults() {
      const unscoredCount = this.reviewResults.filter(result => result.score === null).length;
      console.log("待评分条目数为：", unscoredCount); // 日志
      return unscoredCount;
    },
    majorScoreData() {
      const data = {};
      this.reviewResults.forEach((result) => {
        // 检查 application 和 majorId 是否存在
        const majorId = result.application ? result.application.majorId : null;
        if (!majorId) return;  // 跳过没有 majorId 的条目

        const majorName = this.getMajorName(majorId);
        if (!data[majorName]) {
          data[majorName] = {count: 0, totalScore: 0};
        }
        data[majorName].count += 1;
        if (result.score !== null) {
          data[majorName].totalScore += result.score;
        }
      });

      // 计算平均分
      Object.keys(data).forEach((major) => {
        data[major].averageScore = data[major].count > 0 ? data[major].totalScore / data[major].count : 0;
        console.log(`专业：${major}，评分条目：${data[major].count}，平均分：${data[major].averageScore}`); // 日志
      });
      return data;
    },
  },
  methods: {
    async fetchReviewResults() {
      try {
        console.log("开始获取评分数据..."); // 日志
        const response = await axios.get(`/review-results/collegeId`, {
          params: {collegeId: this.departmentIdPrefix}
        });
        this.reviewResults = response.data;
        console.log("评分数据获取成功：", this.reviewResults);  // 检查数据结构
      } catch (error) {
        console.error('获取评分数据失败', error);
      }
    },
    async fetchCollegesAndMajors() {
      try {
        console.log("开始获取学院和专业信息..."); // 日志
        const collegesResponse = await axios.get('/colleges/all');
        this.colleges = collegesResponse.data;
        console.log("学院信息获取成功：", this.colleges); // 日志

        const majorsResponse = await axios.get('/majors/all');
        this.majors = majorsResponse.data;
        console.log("专业信息获取成功：", this.majors); // 日志

        const department = this.colleges.find(
            (c) => parseInt(c.id.toString().substring(0, 2)) === this.departmentIdPrefix
        );
        this.departmentName = department ? department.name : '未知院系';
        console.log("当前院系名称：", this.departmentName); // 日志
      } catch (error) {
        console.error('获取学院和专业信息失败', error);
      }
    },
    getMajorName(majorId) {
      if (!majorId) return '未知专业';  // 确保 majorId 存在
      const major = this.majors.find((m) => m.id === majorId);
      console.log("获取专业名称：", major ? major.name : '未知专业'); // 日志
      return major ? major.name : '未知专业';
    },
  },
  async mounted() {
    const idNumber = localStorage.getItem('idNumber');
    console.log("从 localStorage 获取的 idNumber:", idNumber); // 日志
    if (idNumber && !isNaN(parseInt(idNumber.substring(0, 2)))) {
      this.departmentIdPrefix = parseInt(idNumber.substring(0, 2));
      console.log("解析到的院系 ID 前缀：", this.departmentIdPrefix); // 日志
    } else {
      console.warn("idNumber 无效，无法获取院系 ID 前缀");
      this.departmentIdPrefix = null;
    }

    await this.fetchCollegesAndMajors();  // 确保专业信息已加载
    await this.fetchReviewResults();       // 然后获取评分数据
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
