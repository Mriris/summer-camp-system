// src/main/java/org/mua/service/CollegeService.java
package org.mua.service;

import org.mua.model.College;
import org.mua.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    public College createCollege(College college) {
        if (collegeRepository.existsByName(college.getName())) {
            throw new RuntimeException("该学院已存在");
        }
        return collegeRepository.save(college);
    }

    public Optional<College> getCollegeById(Long id) {
        return collegeRepository.findById(id);
    }

    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    public College updateCollege(Long id, College collegeDetails) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("未找到学院"));

        college.setName(collegeDetails.getName());
        college.setDescription(collegeDetails.getDescription());
        college.setLocation(collegeDetails.getLocation());
        college.setContactEmail(collegeDetails.getContactEmail());
        college.setContactPhone(collegeDetails.getContactPhone());
        college.setWebsiteUrl(collegeDetails.getWebsiteUrl());
        college.setIsCampOpen(collegeDetails.getIsCampOpen());
        college.setCampStartDate(collegeDetails.getCampStartDate());
        college.setCampEndDate(collegeDetails.getCampEndDate());
        college.setCampQuota(collegeDetails.getCampQuota());
        college.setCampRegistrationDeadline(collegeDetails.getCampRegistrationDeadline());

        return collegeRepository.save(college);
    }

    public void deleteCollege(Long id) {
        if (!collegeRepository.existsById(id)) {
            throw new RuntimeException("学院不存在");
        }
        collegeRepository.deleteById(id);
    }
}
