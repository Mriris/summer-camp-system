package org.mua.controller;

import org.mua.model.ReviewResult;
import org.mua.service.ReviewResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review-results")

public class ReviewResultController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewResultController.class);

    @Autowired
    private ReviewResultService reviewResultService;

    /**
     * 使用 Application ID 创建 ReviewResult
     */
    @PostMapping("/application")
    public ResponseEntity<ReviewResult> createReviewResultFromApplication(@RequestBody Map<String, Long> request) {
        Long applicationId = request.get("applicationId");
        try {
            ReviewResult reviewResult = reviewResultService.createReviewResult(applicationId);
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ReviewResult> createReviewResult(@RequestBody Map<String, Long> request) {
        Long applicationId = request.get("applicationId");
        try {
            ReviewResult reviewResult = reviewResultService.createReviewResult(applicationId);
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 更新评分信息
     */
    @PatchMapping("/{applicationId}/score")
    public ResponseEntity<ReviewResult> updateScoreByApplicationId(
            @PathVariable Long applicationId,
            @RequestParam double score) {
        try {
            // 输出日志到控制台
            logger.info("Updating score for applicationId {}: new score = {}", applicationId, score);

            ReviewResult updatedReviewResult = reviewResultService.updateScoreByApplicationId(applicationId, score);
            return ResponseEntity.ok(updatedReviewResult);
        } catch (RuntimeException e) {
            logger.error("Failed to update score for applicationId {}: {}", applicationId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/grade")
    public ResponseEntity<ReviewResult> updateGrade(@PathVariable Long id, @RequestParam ReviewResult.Grade grade) {
        ReviewResult updatedResult = reviewResultService.updateScore(id, grade.ordinal());
        return ResponseEntity.ok(updatedResult);
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<ReviewResult>> getByApplicationId(@PathVariable Long applicationId) {
        System.out.println("获取 applicationId 为 " + applicationId + " 的评分记录");
        List<ReviewResult> results = reviewResultService.getByApplicationId(applicationId);
        return ResponseEntity.ok(results);
    }
    /**
     * 获取特定院系的评分记录
     */
    @GetMapping("/collegeId")
    public ResponseEntity<List<ReviewResult>> getReviewResultsByDepartment(@RequestParam long collegeId) {
        logger.info("Fetching review results for department with ID prefix {}", collegeId);
        List<ReviewResult> results = reviewResultService.getReviewResultsByDepartment(collegeId);
        return ResponseEntity.ok(results);
    }
}
