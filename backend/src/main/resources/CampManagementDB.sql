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

DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS advisors;
DROP TABLE IF EXISTS majors;
DROP TABLE IF EXISTS colleges;

CREATE TABLE colleges (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE,              -- 学院名称
                          description TEXT,                               -- 学院描述
                          location VARCHAR(100),                          -- 学院位置
                          contact_email VARCHAR(100),                     -- 学院联系邮箱
                          contact_phone VARCHAR(20),                      -- 学院联系电话
                          website_url VARCHAR(255),                       -- 学院官网链接
                          is_camp_open BOOLEAN DEFAULT FALSE,             -- 夏令营是否开启，默认关闭
                          camp_start_date DATE,                           -- 夏令营开始日期
                          camp_end_date DATE,                             -- 夏令营结束日期
                          camp_quota INT,                                 -- 夏令营招生名额
                          camp_registration_deadline DATE,                -- 夏令营报名截止日期
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 记录创建时间
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新记录的时间
);
INSERT INTO colleges (name, description, location, contact_email, contact_phone, website_url, is_camp_open, camp_start_date, camp_end_date, camp_quota, camp_registration_deadline) VALUES
                                                                                                                                                                                        ('计算机学院', '提供计算机科学与技术、软件工程等专业', '西山电航楼', 'cs@university.edu', '010-12345678', 'http://cs.university.edu', TRUE, '2024-12-01', '2024-12-10', 100, '2024-12-15'),
                                                                                                                                                                                        ('信息工程学院', '提供网络工程、智能科学与技术等专业', '西山扬帆楼', 'ie@university.edu', '010-87654321', 'http://ie.university.edu', TRUE, '2024-12-05', '2024-12-15', 80, '2024-12-20'),
                                                                                                                                                                                        ('数据科学学院', '研究数据科学与大数据技术领域', '东山知行楼', 'ds@university.edu', '010-13524680', 'http://ds.university.edu', TRUE, '2024-12-10', '2024-12-20', 60, '2024-12-25');

CREATE TABLE majors (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,      -- 专业ID，唯一标识
                        name VARCHAR(100) NOT NULL UNIQUE,         -- 专业名称，设为唯一值
                        college_id BIGINT NOT NULL,                -- 学院ID，外键关联colleges表
                        description TEXT,                          -- 专业描述，可为空
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间，默认当前时间
                        FOREIGN KEY (college_id) REFERENCES colleges(id) ON DELETE CASCADE  -- 外键关联，学院删除则删除该学院的专业
);
INSERT INTO majors (name, college_id, description) VALUES
                                                       ('计算机科学与技术', 1, '研究计算机系统、软件和硬件的基础知识'),
                                                       ('软件工程', 1, '专注于软件开发和项目管理的理论与实践'),
                                                       ('网络工程', 2, '研究计算机网络技术和通信系统'),
                                                       ('智能科学与技术', 2, '专注于人工智能、机器学习等领域的研究'),
                                                       ('数据科学与大数据技术', 3, '研究大数据分析、数据挖掘和机器学习的应用');

CREATE TABLE advisors (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          phone VARCHAR(20),
                          major_id BIGINT NOT NULL,
                          title VARCHAR(50),
                          office_location VARCHAR(100),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (major_id) REFERENCES majors(id) ON DELETE CASCADE
);

INSERT INTO advisors (name, email, phone, major_id, title, office_location, created_at, updated_at) VALUES
                                                                                                        -- 计算机科学与技术专业导师
                                                                                                        ('张伟', 'zhangwei@university.edu', '010-10000001', 1, '教授', '计算机学院101', NOW(), NOW()),
                                                                                                        ('李丽', 'lili@university.edu', '010-10000002', 1, '副教授', '计算机学院102', NOW(), NOW()),
                                                                                                        ('王强', 'wangqiang@university.edu', '010-10000003', 1, '讲师', '计算机学院103', NOW(), NOW()),

                                                                                                        -- 软件工程专业导师
                                                                                                        ('刘杰', 'liujie@university.edu', '010-20000001', 2, '教授', '软件学院201', NOW(), NOW()),
                                                                                                        ('陈芳', 'chenfang@university.edu', '010-20000002', 2, '副教授', '软件学院202', NOW(), NOW()),
                                                                                                        ('孙华', 'sunhua@university.edu', '010-20000003', 2, '讲师', '软件学院203', NOW(), NOW()),

                                                                                                        -- 网络工程专业导师
                                                                                                        ('杨磊', 'yanglei@university.edu', '010-30000001', 3, '教授', '信息工程学院301', NOW(), NOW()),
                                                                                                        ('吴霞', 'wuxia@university.edu', '010-30000002', 3, '副教授', '信息工程学院302', NOW(), NOW()),
                                                                                                        ('赵敏', 'zhaomin@university.edu', '010-30000003', 3, '讲师', '信息工程学院303', NOW(), NOW()),

                                                                                                        -- 智能科学与技术专业导师
                                                                                                        ('何俊', 'hejun@university.edu', '010-40000001', 4, '教授', '信息工程学院401', NOW(), NOW()),
                                                                                                        ('王红', 'wanghong@university.edu', '010-40000002', 4, '副教授', '信息工程学院402', NOW(), NOW()),
                                                                                                        ('郭亮', 'guoliang@university.edu', '010-40000003', 4, '讲师', '信息工程学院403', NOW(), NOW()),

                                                                                                        -- 数据科学与大数据技术专业导师
                                                                                                        ('胡昊', 'huhao@university.edu', '010-50000001', 5, '教授', '数据科学学院501', NOW(), NOW()),
                                                                                                        ('曹颖', 'caoying@university.edu', '010-50000002', 5, '副教授', '数据科学学院502', NOW(), NOW()),
                                                                                                        ('周明', 'zhouming@university.edu', '010-50000003', 5, '讲师', '数据科学学院503', NOW(), NOW());


CREATE TABLE applications (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,                    -- 唯一报名ID
                              user_id BIGINT NOT NULL,                                 -- 报名用户的外键，关联 users 表
                              application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    -- 报名日期，默认为当前时间
                              status ENUM('UNPAID', 'PENDING', 'APPROVED', 'REJECTED') DEFAULT 'UNPAID', -- 报名状态，默认未缴费
                              college_id BIGINT NOT NULL,                              -- 学院编号，外键关联 colleges 表
                              major_id BIGINT NOT NULL,                                -- 专业编号，外键关联 majors 表
                              advisor_id BIGINT,                                       -- 意向导师编号，可为空，外键关联 advisors 表
                              undergraduate_rank INT,                                  -- 本科专业排名
                              total_undergraduate_students INT,                        -- 本科专业总人数
                              awards TEXT,                                             -- 本科所获奖项，作为一个文本字段来记录多个奖项
                              proof_pdf VARCHAR(255),                                  -- 证明材料文件路径（PDF）

                              UNIQUE (user_id),                                        -- 设置 user_id 为唯一，防止重复报名
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,      -- 外键关联用户表，用户被删除则删除报名
                              FOREIGN KEY (college_id) REFERENCES colleges(id) ON DELETE RESTRICT, -- 关联学院表，不允许删除有报名的学院
                              FOREIGN KEY (major_id) REFERENCES majors(id) ON DELETE RESTRICT,   -- 关联专业表，不允许删除有报名的专业
                              FOREIGN KEY (advisor_id) REFERENCES advisors(id) ON DELETE SET NULL -- 关联导师表，导师被删除时设置为空
);

CREATE TABLE review_result (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               application_id BIGINT NOT NULL,
                               score DECIMAL(5, 2) NOT NULL,            -- 院系打的分数
                               grade ENUM('Excellent', 'Pass', 'Fail') DEFAULT NULL,  -- 学校后续填入的成绩，默认为 NULL
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (application_id) REFERENCES applications(id)
);
INSERT INTO review_result (application_id, score)
VALUES
    (4, 88.5);

