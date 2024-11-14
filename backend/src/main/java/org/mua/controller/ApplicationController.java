package org.mua.controller;

import org.mua.model.Application;
import org.mua.model.User;
import org.mua.service.ApplicationService;
import org.mua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mua.dto.ApplicationReviewOverviewDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
     * 获取所有报名信息
     */
    @GetMapping("/list")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }


    /**
     * 提交新的报名信息，支持 multipart 表单数据
     */
    @PostMapping(value = "/submit", consumes = "multipart/form-data")
    public ResponseEntity<Application> submitApplication(
            @RequestParam Long userId,
            @RequestParam Long collegeId,
            @RequestParam Long majorId,
            @RequestParam(required = false) Long advisorId,
            @RequestParam int undergraduateRank,
            @RequestParam int totalUndergraduateStudents,
            @RequestParam String awards,
            @RequestPart(value = "proofPdf", required = false) MultipartFile proofPdf) {

        logger.info("Received submit request with userId: {}", userId);

        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            Application application = new Application();
            application.setUser(userOpt.get());
            application.setCollegeId(collegeId);
            application.setMajorId(majorId);
            application.setAdvisorId(advisorId);
            application.setUndergraduateRank(undergraduateRank);
            application.setTotalUndergraduateStudents(totalUndergraduateStudents);
            application.setAwards(awards);

            // 保存 PDF 文件
            if (proofPdf != null && !proofPdf.isEmpty()) {
                try {
                    String filePath = saveProofPdf(proofPdf,userId);
                    application.setProofPdf(filePath);
                } catch (IOException e) {
                    logger.error("Error saving proofPdf file", e);
                    return ResponseEntity.internalServerError().body(null);
                }
            }

            Application submittedApp = applicationService.submitApplication(application, userOpt.get());
            logger.info("报名提交成功，报名ID: " + submittedApp.getId());
            return ResponseEntity.ok(submittedApp);
        }

        logger.warn("提交失败，未找到对应的用户ID: " + userId);
        return ResponseEntity.badRequest().body(null);
    }


    /**
     * 保存证明材料 PDF 文件
     */
    private String saveProofPdf(MultipartFile proofPdf, Long userId) throws IOException {
        // 获取项目根目录的 uploads 文件夹路径
        String uploadsDir = System.getProperty("user.dir") + File.separator+"backend"+ File.separator + "uploads";
        File uploadsFolder = new File(uploadsDir);

        // 如果 uploads 文件夹不存在，则创建它
        if (!uploadsFolder.exists()) {
            uploadsFolder.mkdirs();
        }

        // 使用 userId 作为文件名，并保持 .pdf 扩展名
        String filePath = uploadsDir + File.separator + userId + ".pdf";
        File destinationFile = new File(filePath);

        // 保存文件
        proofPdf.transferTo(destinationFile);

        return filePath;
    }

    /**
     * 根据报名ID查询报名信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable Long id) {
        Optional<Application> applicationOpt = applicationService.getApplicationById(id);
        return applicationOpt.map(ResponseEntity::ok).orElseGet(() -> {
            System.out.println("未找到报名ID为 " + id + " 的报名信息");
            return ResponseEntity.notFound().build();
        });
    }

    /**
     * 根据 userId 查询用户的报名信息
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Application> getApplicationByUserId(@PathVariable Long userId) {
        Optional<Application> applicationOpt = applicationService.getApplicationByUserId(userId);
        if (applicationOpt.isPresent()) {
            Application application = applicationOpt.get();

            // 构建下载链接的完整 URL
            String proofPdfPath = application.getProofPdf();
            if (proofPdfPath != null && !proofPdfPath.isEmpty()) {
                String baseUrl = "http://localhost:8081/uploads/";  // 服务器路径
                application.setProofPdf(baseUrl + new File(proofPdfPath).getName());
            }

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
        try {
            Application updatedApplication = applicationService.updateApplicationStatus(id, status);
            System.out.println("状态更新成功，当前状态: " + updatedApplication.getStatus());
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            System.out.println("更新失败，未找到报名ID为 " + id + " 的报名信息");
            return ResponseEntity.notFound().build();
        }
    }
    // 获取所有待审核的申请
    @GetMapping("/pending")
    public ResponseEntity<List<Application>> getPendingApplications() {
        List<Application> pendingApplications = applicationService.getStatusNotApplications();
        return ResponseEntity.ok(pendingApplications);
    }
    /**
     * 根据状态查询申请
     */
    @GetMapping("/status")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@RequestParam List<Application.Status> statuses) {
        List<Application> applications = applicationService.getApplicationsByStatus(statuses);
        return ResponseEntity.ok(applications);
    }
    /**
     * 更新报名信息，包括学院、专业、导师、以及新增的报名详细信息
     */
    @PatchMapping(value = "/update/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Application> updateApplication(
            @RequestParam Long userId,
            @PathVariable Long id,
            @RequestParam Long collegeId,
            @RequestParam Long majorId,
            @RequestParam(required = false) Long advisorId,
            @RequestParam int undergraduateRank,
            @RequestParam int totalUndergraduateStudents,
            @RequestParam String awards,
            @RequestPart(value = "proofPdf", required = false) MultipartFile proofPdf) {

        System.out.println("收到报名信息更新请求：");
        System.out.println("报名ID: " + id);
        System.out.println("更新的学院号: " + collegeId);
        System.out.println("更新的专业号: " + majorId);
        System.out.println("更新的导师号: " + advisorId);
        System.out.println("更新的本科排名: " + undergraduateRank);
        System.out.println("更新的本科总人数: " + totalUndergraduateStudents);
        System.out.println("更新的获奖情况: " + awards);
        System.out.println("更新的证明材料文件: " + (proofPdf != null ? proofPdf.getOriginalFilename() : "无文件"));

        try {
            // 查找现有申请
            Application existingApplication = applicationService.getApplicationById(id)
                    .orElseThrow(() -> new RuntimeException("未找到报名ID为 " + id + " 的报名信息"));

            // 更新字段
            existingApplication.setCollegeId(collegeId);
            existingApplication.setMajorId(majorId);
            existingApplication.setAdvisorId(advisorId);
            existingApplication.setUndergraduateRank(undergraduateRank);
            existingApplication.setTotalUndergraduateStudents(totalUndergraduateStudents);
            existingApplication.setAwards(awards);

            // 更新文件
            if (proofPdf != null && !proofPdf.isEmpty()) {
                String filePath = saveProofPdf(proofPdf,userId);
                existingApplication.setProofPdf(filePath);
            }

            Application updatedApplication = applicationService.updateApplicationDetails(id, existingApplication);
            System.out.println("报名信息更新成功，当前状态: " + updatedApplication.getStatus());
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException | IOException e) {
            System.out.println("更新失败，原因: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    /**
     * 获取学院夏令营情况概览，带有状态、专业和评分信息
     */
    @GetMapping("/overview")
    public ResponseEntity<List<ApplicationReviewOverviewDTO>> getDepartmentOverview(
            @RequestParam Long departmentId) {
        logger.info("调用 getDepartmentOverview API，departmentId: " + departmentId);
        List<ApplicationReviewOverviewDTO> overviewData = applicationService.getDepartmentOverview(departmentId);
        logger.info("返回的概要数据: " + overviewData);
        return ResponseEntity.ok(overviewData);
    }
    /**
     * 根据 applicationId 获取 application 信息
     */
    @GetMapping("/detail/{applicationId}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long applicationId) {
        // 获取 Optional<Application> 对象
        Optional<Application> applicationOpt = applicationService.getApplicationById(applicationId);

        // 如果存在 Application 对象，返回它；否则返回 404
        if (applicationOpt.isPresent()) {
            return ResponseEntity.ok(applicationOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
