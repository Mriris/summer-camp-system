package org.mua.repository;

import org.mua.model.ReviewResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewResultRepository extends JpaRepository<ReviewResult, Long> {
    List<ReviewResult> findByApplicationId(Long applicationId);
}
