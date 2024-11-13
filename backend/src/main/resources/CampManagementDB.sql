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
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       role INT DEFAULT 0 CHECK (role IN (0, 1, 2)) -- 角色字段：0为普通用户，1为学院负责人，2为学校管理员
);


-- 添加索引
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);

INSERT INTO users (username, email, password,id_number,role) VALUES ('mua', 'mua@dlmu.edu.cn', '12345678','000',2);

DROP TABLE enrolled_students;
CREATE TABLE enrolled_students (
                                   student_id VARCHAR(20) PRIMARY KEY,   -- 学籍号
                                   username VARCHAR(50) NOT NULL,        -- 姓名
                                   class VARCHAR(100) NOT NULL,          -- 班级
                                   contact_info VARCHAR(20),             -- 联系方式
                                   id_number VARCHAR(18) UNIQUE NOT NULL, -- 身份证号，设为唯一值
                                   gender INT CHECK (gender IN (1, 2))   -- 性别：1为男，2为女
);

DROP TABLE applications;
CREATE TABLE applications (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,             -- 唯一报名ID
                              user_id BIGINT NOT NULL,                          -- 报名用户的外键，关联 users 表
                              application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 报名日期，默认为当前时间
                              status ENUM('UNPAID', 'PENDING', 'APPROVED', 'REJECTED') DEFAULT 'UNPAID', -- 报名状态，默认未缴费
                              college_id INT NOT NULL,                          -- 学院编号
                              major_id INT NOT NULL,                            -- 专业编号
                              advisor_id INT,                                   -- 意向导师编号，允许为空
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE -- 外键关联用户表，用户被删除则删除对应报名记录
);


