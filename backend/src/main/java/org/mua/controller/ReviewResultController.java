package org.mua.controller;

import org.mua.model.ReviewResult;
import org.mua.service.ReviewResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review-results")
public class ReviewResultController {

    @Autowired
    private ReviewResultService reviewResultService;

    @PostMapping
    public ResponseEntity<ReviewResult> createReviewResult(@RequestBody ReviewResult reviewResult) {
        ReviewResult savedResult = reviewResultService.saveReviewResult(reviewResult);
        return ResponseEntity.ok(savedResult);
    }

    @PatchMapping("/{id}/grade")
    public ResponseEntity<ReviewResult> updateGrade(@PathVariable Long id, @RequestParam ReviewResult.Grade grade) {
        ReviewResult updatedResult = reviewResultService.updateGrade(id, grade);
        return ResponseEntity.ok(updatedResult);
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<ReviewResult>> getByApplicationId(@PathVariable Long applicationId) {
        System.out.println("获取 applicationId 为 " + applicationId + " 的评分记录");
        List<ReviewResult> results = reviewResultService.getByApplicationId(applicationId);
        return ResponseEntity.ok(results);
    }
}
