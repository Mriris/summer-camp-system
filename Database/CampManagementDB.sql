-- 示例用户表
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('STUDENT', 'ADMIN', 'REVIEWER') NOT NULL
);

-- 报名信息表
CREATE TABLE registrations (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  activity_id INT,
  status ENUM('PENDING', 'APPROVED', 'REJECTED'),
  FOREIGN KEY (user_id) REFERENCES users(id)
);
