package org.mua.service;

import org.mua.model.User;
import org.mua.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 注册用户并检查用户名、邮箱、身份证号的唯一性
     */
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())
                || userRepository.existsByEmail(user.getEmail())
                || userRepository.existsByIdNumber(user.getIdNumber())) {
            throw new RuntimeException("Username, Email, or ID Number already exists");
        }
        return userRepository.save(user);
    }

    /**
     * 根据用户名查找用户
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
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
            // 通过邮箱查找用户
            user = userRepository.findByEmail(emailOrIdNumber);
        } else {
            // 通过身份证号查找用户
            user = userRepository.findByIdNumber(emailOrIdNumber);
        }

        // 检查用户是否存在并匹配密码
        if (user.isPresent() && password.equals(user.get().getPassword())) {
            return user.get(); // 返回用户对象
        }
        return null; // 登录失败返回 null
    }
}
