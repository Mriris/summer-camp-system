package org.mua.service;

import org.mua.dto.ApplicationReviewOverviewDTO;
import org.mua.model.Application;
import org.mua.model.User;
import org.mua.repository.ApplicationRepository;
import org.mua.repository.CollegeRepository;
import org.mua.repository.MajorRepository;
import org.mua.repository.AdvisorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

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

        // 设置其他默认字段并添加新字段值
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

    public List<Application> getStatusNotApplications() {
        return applicationRepository.findByStatusNot(Application.Status.UNPAID);
//        return applicationRepository.findByStatus(Application.Status.PENDING);
    }
    /**
     * 更新报名详细信息，包括学院、专业、导师以及新增的详细字段
     */
    public Application updateApplicationDetails(Long applicationId, Application updatedApplicationData) {
        Optional<Application> applicationOpt = applicationRepository.findById(applicationId);
        if (applicationOpt.isPresent()) {
            Application existingApplication = applicationOpt.get();

            // 更新学院、专业和导师信息
            existingApplication.setCollegeId(updatedApplicationData.getCollegeId());
            existingApplication.setMajorId(updatedApplicationData.getMajorId());
            existingApplication.setAdvisorId(updatedApplicationData.getAdvisorId());

            // 更新本科专业排名、人数、所获奖项和证明材料
            existingApplication.setUndergraduateRank(updatedApplicationData.getUndergraduateRank());
            existingApplication.setTotalUndergraduateStudents(updatedApplicationData.getTotalUndergraduateStudents());
            existingApplication.setAwards(updatedApplicationData.getAwards());
            existingApplication.setProofPdf(updatedApplicationData.getProofPdf());

            return applicationRepository.save(existingApplication);
        }
        throw new RuntimeException("未找到指定报名");
    }

    public Optional<Application> getApplicationByUserId(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    public List<Application> getApplicationsByStatus(List<Application.Status> statuses) {
        return applicationRepository.findByStatusIn(statuses);
    }
    /**
     * 获取学院概览数据，包括应用程序的评分和状态信息。
     */
    public List<ApplicationReviewOverviewDTO> getDepartmentOverview(Long departmentId) {
        logger.info("开始获取学院 ID 为 {} 的夏令营概览数据", departmentId);

        List<ApplicationReviewOverviewDTO> overviewData = applicationRepository.findOverviewByDepartment(departmentId);

        if (overviewData.isEmpty()) {
            logger.warn("未找到学院 ID 为 {} 的相关申请数据", departmentId);
        } else {
            overviewData.forEach(dto -> {
                logger.info("查询结果 - Application ID: {}, Major ID: {}, User ID: {}, Score: {}, Grade: {}",
                        dto.getApplicationId(),
                        dto.getMajorId(),
                        dto.getUserId(),
                        dto.getScore(),
                        dto.getGrade());
            });
        }

        return overviewData;
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
