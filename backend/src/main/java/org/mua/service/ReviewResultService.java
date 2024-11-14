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

    public ReviewResult saveReviewResult(ReviewResult reviewResult) {
        return reviewResultRepository.save(reviewResult);
    }

    public ReviewResult updateGrade(Long id, ReviewResult.Grade grade) {
        ReviewResult reviewResult = reviewResultRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        reviewResult.setGrade(grade);
        return reviewResultRepository.save(reviewResult);
    }

    public List<ReviewResult> getByApplicationId(Long applicationId) {
        return reviewResultRepository.findByApplicationId(applicationId);
    }
}
