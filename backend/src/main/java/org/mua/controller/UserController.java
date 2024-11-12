package org.mua.controller;

import org.mua.model.User;
import org.mua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")  // 允许跨域请求
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        System.out.println("收到注册信息：");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        System.out.println("ID Number: " + user.getIdNumber());

        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("注册失败: " + e.getMessage());
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
        System.out.println("Email or ID Number: " + emailOrIdNumber);
        System.out.println("Password: " + password);

        User user = userService.validateUser(emailOrIdNumber, password);
        if (user != null) {
            System.out.println("登录成功");

            // 构造返回的数据，包括消息和用户名
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", user.getUsername()); // 返回用户名

            return ResponseEntity.ok(response);
        } else {
            System.out.println("登录失败");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid email/ID number or password");

            return ResponseEntity.status(401).body(errorResponse);
        }
    }
}
