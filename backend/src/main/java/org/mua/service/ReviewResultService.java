package org.mua.service;

import org.mua.model.ReviewResult;
import org.mua.repository.ReviewResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewResultService {

    @Autowired
    private ReviewResultRepository reviewResultRepository;

    // 保存评分
    public ReviewResult saveReviewResult(ReviewResult reviewResult) {
        return reviewResultRepository.save(reviewResult);
    }

    // 更新评分
    public ReviewResult updateGrade(Long id, double score) {
        ReviewResult reviewResult = reviewResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReviewResult not found"));
        reviewResult.setScore(score);
        return reviewResultRepository.save(reviewResult);
    }

    // 根据申请ID获取评分
    public List<ReviewResult> getByApplicationId(Long applicationId) {
        return reviewResultRepository.findByApplicationId(applicationId);
    }
}
