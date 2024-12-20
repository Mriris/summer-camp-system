package org.mua.controller;

import org.mua.model.User;
import org.mua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")  // 允许跨域请求
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册用户，检查在籍学生身份
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        System.out.println("收到注册信息：");
        System.out.println("用户名: " + user.getUsername());
        System.out.println("密码: " + user.getPassword());
        System.out.println("身份证号: " + user.getIdNumber());

        try {
            userService.registerUser(user);
            return ResponseEntity.ok("用户注册成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> loginData) {
        String emailOrIdNumber = loginData.get("emailOrIdNumber");
        String password = loginData.get("password");

        System.out.println("收到登录信息：");
        System.out.println("邮箱或身份证号: " + emailOrIdNumber);
        System.out.println("密码: " + password);

        User user = userService.validateUser(emailOrIdNumber, password);
        if (user != null) {
            System.out.println("登录成功");

            // 构造返回的数据，包括消息和用户名
            Map<String, String> response = new HashMap<>();
            response.put("message", "登录成功");
            response.put("username", user.getUsername()); // 返回用户名
            response.put("role", String.valueOf(user.getRole()));  // 返回角色
            response.put("id", String.valueOf(user.getId()));  // 返回id
            response.put("idNumber", String.valueOf(user.getIdNumber()));
            System.out.println(user.getIdNumber());
            return ResponseEntity.ok(response);
        } else {
            System.out.println("登录失败");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "邮箱或身份证号或密码无效");

            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    /**
     * 根据用户ID获取用户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        System.out.println("收到用户信息查询请求，用户ID: " + id);

        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            System.out.println("未找到ID为 " + id + " 的用户信息");
            return ResponseEntity.notFound().build();
        }
    }
}
