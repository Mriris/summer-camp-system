// src/main/java/org/mua/controller/MajorController.java
package org.mua.controller;

import org.mua.model.Major;
import org.mua.model.College;
import org.mua.service.MajorService;
import org.mua.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/majors")
@CrossOrigin(origins = "*")  // 允许跨域请求
public class MajorController {

    @Autowired
    private MajorService majorService;

    @Autowired
    private CollegeService collegeService;

    @PostMapping("/create")
    public ResponseEntity<Major> createMajor(@RequestBody Major major) {
        try {
            Major createdMajor = majorService.createMajor(major);
            return ResponseEntity.ok(createdMajor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Major> getMajorById(@PathVariable Long id) {
        return majorService.getMajorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Major> getAllMajors() {
        return majorService.getAllMajors();
    }

    @GetMapping("/college/{collegeId}")
    public ResponseEntity<List<Major>> getMajorsByCollege(@PathVariable Long collegeId) {
        Optional<College> collegeOpt = collegeService.getCollegeById(collegeId);
        if (collegeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Major> majors = majorService.getMajorsByCollege(collegeOpt.get());
        return ResponseEntity.ok(majors);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Major> updateMajor(@PathVariable Long id, @RequestBody Major majorDetails) {
        try {
            Major updatedMajor = majorService.updateMajor(id, majorDetails);
            return ResponseEntity.ok(updatedMajor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable Long id) {
        try {
            majorService.deleteMajor(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
