// src/main/java/org/mua/controller/UserController.java
package org.mua.controller;

import org.mua.model.User;
import org.mua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")  // 允许来自前端的跨域请求
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // 输出收到的用户注册信息
        System.out.println("收到注册信息：");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // 输出收到的用户登录信息
        System.out.println("收到登录信息：");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        boolean isValid = userService.validateUser(user.getUsername(), user.getPassword());
        if (isValid) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
