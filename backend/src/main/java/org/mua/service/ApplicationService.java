package org.mua.service;

import org.mua.model.Application;
import org.mua.model.User;
import org.mua.repository.ApplicationRepository;
import org.mua.repository.CollegeRepository;
import org.mua.repository.MajorRepository;
import org.mua.repository.AdvisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private AdvisorRepository advisorRepository;

    /**
     * 提交新的报名，检查是否已存在相同用户的报名记录，并验证学院、专业和导师的匹配性。
     */
    public Application submitApplication(Application application, User user) {
        // 检查用户是否已报名
        if (applicationRepository.existsByUser(user)) {
            throw new RuntimeException("该用户已报名，不能重复报名");
        }

        // 验证学院、专业和导师的存在性和匹配性
        boolean collegeExists = collegeRepository.existsById(application.getCollegeId());
        boolean majorExists = majorRepository.existsByIdAndCollegeId(application.getMajorId(), application.getCollegeId());
        boolean advisorExists = advisorRepository.existsByIdAndMajorId(application.getAdvisorId(), application.getMajorId());

        if (!collegeExists) {
            throw new RuntimeException("指定的学院不存在");
        }
        if (!majorExists) {
            throw new RuntimeException("指定的专业与学院不匹配");
        }
        if (application.getAdvisorId() != null && !advisorExists) {
            throw new RuntimeException("指定的导师与专业不匹配");
        }

        // 设置其他默认字段
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