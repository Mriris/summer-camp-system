// src/main/java/org/mua/service/ApplicationService.java
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

    public Application submitApplication(Application application, User user) {
        application.setUser(user);
        application.setStatus(Application.Status.UNPAID);  // 默认设置为未缴费
        return applicationRepository.save(application);
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    // 根据用户获取申请列表
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
        throw new RuntimeException("Application not found");
    }
}
