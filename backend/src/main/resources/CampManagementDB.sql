-- 使用数据库
USE campmanagementdb;

-- 创建用户表
DROP TABLE users;
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       id_number VARCHAR(18) UNIQUE NOT NULL, -- 身份证号，设为唯一值
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 添加索引
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);

INSERT INTO users (username, email, password,id_number) VALUES ('mua', 'mua@dlmu.edu.cn', '12345678','000');

DROP TABLE enrolled_students;
CREATE TABLE enrolled_students (
                                   student_id VARCHAR(20) PRIMARY KEY,   -- 学籍号
                                   username VARCHAR(50) NOT NULL,        -- 姓名
                                   class VARCHAR(100) NOT NULL,          -- 班级
                                   contact_info VARCHAR(20),             -- 联系方式
                                   id_number VARCHAR(18) UNIQUE NOT NULL, -- 身份证号，设为唯一值
                                   gender INT CHECK (gender IN (1, 2))   -- 性别：1为男，2为女
);


