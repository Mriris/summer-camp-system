package org.mua.repository;

import org.mua.model.Application;
import org.mua.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);

    // 新增此方法，用于检查用户是否已有报名记录
    boolean existsByUser(User user);

    Optional<Application> findByUserId(Long userId);

    List<Application> findByStatus(Application.Status status);

    List<Application> findByStatusNot(Application.Status status);

    List<Application> findByStatusIn(List<Application.Status> statuses);

}
