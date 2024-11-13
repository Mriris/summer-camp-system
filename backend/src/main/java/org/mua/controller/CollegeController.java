// src/main/java/org/mua/controller/CollegeController.java
package org.mua.controller;

import org.mua.model.College;
import org.mua.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colleges")
@CrossOrigin(origins = "*")  // 允许跨域请求
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @PostMapping("/create")
    public ResponseEntity<College> createCollege(@RequestBody College college) {
        try {
            College createdCollege = collegeService.createCollege(college);
            return ResponseEntity.ok(createdCollege);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long id) {
        return collegeService.getCollegeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<College> getAllColleges() {
        return collegeService.getAllColleges();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id, @RequestBody College collegeDetails) {
        try {
            College updatedCollege = collegeService.updateCollege(id, collegeDetails);
            return ResponseEntity.ok(updatedCollege);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        try {
            collegeService.deleteCollege(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
