package org.mua.controller;

import org.mua.model.Application;
import org.mua.model.User;
import org.mua.service.ApplicationService;
import org.mua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    /**
     * 提交新的报名信息
     */
    @PostMapping("/submit")
    public ResponseEntity<Application> submitApplication(@RequestBody Application application, @RequestParam Long userId) {
        logger.info("Received submit request with userId: {}", userId);
        logger.info("Application data: {}", application);
        System.out.println("收到新的报名请求：");
        System.out.println("用户ID: " + userId);
        System.out.println("报名学院号: " + application.getCollegeId());
        System.out.println("报名专业号: " + application.getMajorId());
        System.out.println("意向导师号: " + application.getAdvisorId());
        System.out.println("报名状态: " + application.getStatus());

        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            Application submittedApp = applicationService.submitApplication(application, userOpt.get());
            System.out.println("报名提交成功，报名ID: " + submittedApp.getId());
            return ResponseEntity.ok(submittedApp);
        }

        System.out.println("提交失败，未找到对应的用户ID: " + userId);
        return ResponseEntity.badRequest().body(null);
    }

    /**
     * 根据报名ID查询报名信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable Long id) {
        System.out.println("收到查询请求，报名ID: " + id);

        Optional<Application> applicationOpt = applicationService.getApplicationById(id);
        if (applicationOpt.isPresent()) {
            Application application = applicationOpt.get();
            System.out.println("查询成功，报名信息如下：");
            System.out.println("用户ID: " + application.getUser().getId());
            System.out.println("报名状态: " + application.getStatus());
            System.out.println("报名日期: " + application.getApplicationDate());
            return ResponseEntity.ok(application);
        }

        System.out.println("未找到报名ID为 " + id + " 的报名信息");
        return ResponseEntity.notFound().build();
    }
    /**
     * 根据 userId 查询用户的报名信息
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Application> getApplicationByUserId(@PathVariable Long userId) {
        System.out.println("收到根据 userId 查询报名信息的请求，用户ID: " + userId);

        Optional<Application> applicationOpt = applicationService.getApplicationByUserId(userId);
        if (applicationOpt.isPresent()) {
            Application application = applicationOpt.get();
            System.out.println("查询成功，报名信息如下：");
            System.out.println("用户ID: " + application.getUser().getId());
            System.out.println("报名状态: " + application.getStatus());
            System.out.println("报名日期: " + application.getApplicationDate());
            return ResponseEntity.ok(application);
        }

        System.out.println("未找到用户ID为 " + userId + " 的报名信息");
        return ResponseEntity.notFound().build();
    }

    /**
     * 更新报名状态
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long id, @RequestParam Application.Status status) {
        System.out.println("收到状态更新请求：");
        System.out.println("报名ID: " + id);
        System.out.println("新的状态: " + status);

        try {
            Application updatedApplication = applicationService.updateApplicationStatus(id, status);
            System.out.println("状态更新成功，当前状态: " + updatedApplication.getStatus());
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            System.out.println("更新失败，未找到报名ID为 " + id + " 的报名信息");
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * 更新报名信息（如学院、专业、导师）
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application updatedApplicationData) {
        System.out.println("收到报名信息更新请求：");
        System.out.println("报名ID: " + id);
        System.out.println("更新的学院号: " + updatedApplicationData.getCollegeId());
        System.out.println("更新的专业号: " + updatedApplicationData.getMajorId());
        System.out.println("更新的导师号: " + updatedApplicationData.getAdvisorId());

        try {
            Application updatedApplication = applicationService.updateApplicationDetails(id, updatedApplicationData);
            System.out.println("报名信息更新成功，当前状态: " + updatedApplication.getStatus());
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            System.out.println("更新失败，未找到报名ID为 " + id + " 的报名信息");
            return ResponseEntity.notFound().build();
        }
    }

}
