// src/main/java/org/mua/service/MajorService.java
package org.mua.service;

import org.mua.model.Major;
import org.mua.model.College;
import org.mua.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MajorService {

    @Autowired
    private MajorRepository majorRepository;

    public Major createMajor(Major major) {
        if (majorRepository.existsByName(major.getName())) {
            throw new RuntimeException("该专业已存在");
        }
        return majorRepository.save(major);
    }

    public Optional<Major> getMajorById(Long id) {
        return majorRepository.findById(id);
    }

    public List<Major> getAllMajors() {
        return majorRepository.findAll();
    }

    public List<Major> getMajorsByCollege(College college) {
        return majorRepository.findByCollege(college);
    }

    public Major updateMajor(Long id, Major majorDetails) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("未找到专业"));

        major.setName(majorDetails.getName());
        major.setDescription(majorDetails.getDescription());
        major.setCollege(majorDetails.getCollege());

        return majorRepository.save(major);
    }

    public void deleteMajor(Long id) {
        if (!majorRepository.existsById(id)) {
            throw new RuntimeException("专业不存在");
        }
        majorRepository.deleteById(id);
    }
}
