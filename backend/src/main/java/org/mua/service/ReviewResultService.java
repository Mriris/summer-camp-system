package org.mua.service;

import org.mua.model.ReviewResult;
import org.mua.repository.ReviewResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewResultService {

    @Autowired
    private ReviewResultRepository reviewResultRepository;

    // 保存评分
    public ReviewResult saveReviewResult(ReviewResult reviewResult) {
        return reviewResultRepository.save(reviewResult);
    }

    /**
     * 更新评分信息
     *
     * @param id    ReviewResult的ID
     * @param score 新的评分分数
     * @return      更新后的ReviewResult对象
     */
    public ReviewResult updateScore(Long id, double score) {
        ReviewResult reviewResult = reviewResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("未找到评分记录ID为 " + id + " 的记录"));

        reviewResult.setScore(score);
        return reviewResultRepository.save(reviewResult);
    }

    // 根据申请ID获取评分
    public List<ReviewResult> getByApplicationId(Long applicationId) {
        return reviewResultRepository.findByApplicationId(applicationId);
    }
    public Optional<Long> findReviewResultIdByApplicationId(Long applicationId) {
        return reviewResultRepository.findIdByApplicationId(applicationId)
                .map(ReviewResult::getId);
    }

    public ReviewResult updateScoreByApplicationId(Long applicationId, double score) {
        Long reviewResultId = findReviewResultIdByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("ReviewResult ID not found for applicationId " + applicationId));
        ReviewResult reviewResult = reviewResultRepository.findById(reviewResultId)
                .orElseThrow(() -> new RuntimeException("ReviewResult not found with ID " + reviewResultId));
        reviewResult.setScore(score);
        return reviewResultRepository.save(reviewResult);
    }

    public ReviewResult createReviewResult(Long applicationId) {
        ReviewResult reviewResult = new ReviewResult();
        reviewResult.setApplicationId(applicationId);
        return reviewResultRepository.save(reviewResult);
    }

    public List<ReviewResult> getReviewResultsByDepartment(long departmentIdPrefix) {
        return reviewResultRepository.findByCollegeId(departmentIdPrefix);
    }

}
