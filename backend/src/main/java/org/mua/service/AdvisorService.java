// src/main/java/org/mua/service/AdvisorService.java
package org.mua.service;

import org.mua.model.Advisor;
import org.mua.model.Major;
import org.mua.repository.AdvisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdvisorService {

    @Autowired
    private AdvisorRepository advisorRepository;

    public Advisor createAdvisor(Advisor advisor) {
        if (advisorRepository.existsByEmail(advisor.getEmail())) {
            throw new RuntimeException("该邮箱已被使用");
        }
        return advisorRepository.save(advisor);
    }

    public Optional<Advisor> getAdvisorById(Long id) {
        return advisorRepository.findById(id);
    }

    public List<Advisor> getAllAdvisors() {
        return advisorRepository.findAll();
    }

    public List<Advisor> getAdvisorsByMajor(Major major) {
        return advisorRepository.findByMajor(major);
    }

    public Advisor updateAdvisor(Long id, Advisor advisorDetails) {
        Advisor advisor = advisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("未找到该导师"));

        advisor.setName(advisorDetails.getName());
        advisor.setEmail(advisorDetails.getEmail());
        advisor.setPhone(advisorDetails.getPhone());
        advisor.setMajor(advisorDetails.getMajor());
        advisor.setTitle(advisorDetails.getTitle());
        advisor.setOfficeLocation(advisorDetails.getOfficeLocation());

        return advisorRepository.save(advisor);
    }

    public void deleteAdvisor(Long id) {
        if (!advisorRepository.existsById(id)) {
            throw new RuntimeException("导师不存在");
        }
        advisorRepository.deleteById(id);
    }
}
