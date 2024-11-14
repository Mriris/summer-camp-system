package org.mua.repository;

import org.mua.dto.ApplicationReviewOverviewDTO;
import org.mua.model.Application;
import org.mua.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);

    // 新增此方法，用于检查用户是否已有报名记录
    boolean existsByUser(User user);

    Optional<Application> findByUserId(Long userId);

    List<Application> findByStatus(Application.Status status);

    List<Application> findByStatusNot(Application.Status status);

    @Query("SELECT new org.mua.dto.ApplicationReviewOverviewDTO(a.id as applicationId, a.user.id as userId, a.user.username as userName, a.collegeId, a.majorId, a.status as status, r.score as score, r.grade as grade) " +
            "FROM Application a " +
            "LEFT JOIN ReviewResult r ON a.id = r.applicationId " +
            "WHERE a.collegeId = :collegeId")
    List<ApplicationReviewOverviewDTO> findOverviewByDepartment(@Param("collegeId") Long collegeId);


    List<Application> findByStatusIn(List<Application.Status> statuses);
}
