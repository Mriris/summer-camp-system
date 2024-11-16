
# 夏令营管理系统

## 项目简介
夏令营管理系统是一个专为高校设计的夏令营管理平台，旨在简化学生的报名流程、提高审核效率，并支持优秀营员的筛选与管理。系统覆盖了从学生注册到最终评选的全过程。

## 功能模块
- **学生注册与申报**：学生可以在线注册并提交申请信息。
- **审核模块**：支持院系和校级的多级审核机制，提升审核流程效率。
- **优秀营员评选**：结合学生表现，筛选出优秀营员。
- **用户管理**：系统管理员可管理用户账户及权限。

## 技术栈
- **前端**：Vue.js
- **后端**：Spring Boot + MyBatis
- **数据库**：MySQL
- **运行环境**：Ubuntu 24.04

## 快速启动

### 1. 克隆代码仓库
使用以下命令克隆代码到本地：
```bash
git clone https://github.com/Mriris/summer-camp-system.git
```

### 2. 配置开发环境

#### 前端
1. 进入前端目录：
   ```bash
   cd frontend
   ```
2. 安装依赖：
   ```bash
   npm install
   ```

#### 后端
1. 确保您已经安装了 Maven 和 Java 17。
2. 配置 MySQL 数据库：
   - 数据库名称：`campmanagementdb`
   - 在 MySQL 中运行以下脚本文件以创建数据库和表：
     ```bash
     backend/src/main/resources/CampManagementDB.sql
     ```

### 3. 启动项目

#### 启动前端服务
在前端frontend目录下运行以下命令：
```bash
npm run server
```

#### 启动后端服务
在主目录下，通过以下方式运行主类：
```bash
java -jar backend/src/main/java/org/mua/BackendApplication.java
```

### 4. 访问系统
默认情况下，前端服务会在 [http://localhost:8080](http://localhost:8080) 提供访问，确保后端服务正常运行以支持数据交互。
