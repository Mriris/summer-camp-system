package org.mua.service;

import org.mua.model.User;
import org.mua.model.EnrolledStudent;
import org.mua.repository.UserRepository;
import org.mua.repository.EnrolledStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrolledStudentRepository enrolledStudentRepository;

    /**
     * 注册用户并检查在籍学生身份、邮箱唯一性
     */
    public User registerUser(User user) {
        // 检查在籍学生身份
        Optional<EnrolledStudent> enrolledStudent = enrolledStudentRepository.findByUsernameAndIdNumber(user.getUsername(), user.getIdNumber());
        if (enrolledStudent.isEmpty()) {
            throw new RuntimeException("用户不是在籍学生，无法注册");
        }

        // 检查邮箱的唯一性
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }

        // 检查用户名和身份证号的唯一性
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByIdNumber(user.getIdNumber())) {
            throw new RuntimeException("用户名或身份证号已存在");
        }

        return userRepository.save(user);
    }

    /**
     * 验证用户的登录信息并返回用户对象
     *
     * @param emailOrIdNumber 用户输入的邮箱或身份证号码
     * @param password 用户输入的密码
     * @return User 对象，如果验证成功；否则返回 null
     */
    public User validateUser(String emailOrIdNumber, String password) {
        Optional<User> user;

        if (emailOrIdNumber.contains("@")) {
            user = userRepository.findByEmail(emailOrIdNumber);
        } else {
            user = userRepository.findByIdNumber(emailOrIdNumber);
        }

        // 检查用户是否存在并匹配密码
        if (user.isPresent() && password.equals(user.get().getPassword())) {
            return user.get();
        }
        return null;
    }
}
