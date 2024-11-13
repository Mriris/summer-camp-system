package org.mua.service;

import org.mua.model.Application;
import org.mua.model.User;
import org.mua.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * 提交新的报名，检查是否已存在相同用户的报名记录
     */
    public Application submitApplication(Application application, User user) {
        // 检查用户是否已报名
        if (applicationRepository.existsByUser(user)) {
            throw new RuntimeException("该用户已报名，不能重复报名");
        }

        application.setUser(user);
        application.setStatus(Application.Status.UNPAID);  // 默认状态为未缴费
        return applicationRepository.save(application);
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    // 获取用户的申请列表
    public List<Application> getApplicationsByUser(User user) {
        return applicationRepository.findByUser(user);
    }

    public Application updateApplicationStatus(Long applicationId, Application.Status status) {
        Optional<Application> applicationOpt = applicationRepository.findById(applicationId);
        if (applicationOpt.isPresent()) {
            Application application = applicationOpt.get();
            application.setStatus(status);
            return applicationRepository.save(application);
        }
        throw new RuntimeException("未找到申请记录");
    }
}
