// src/main/java/org/mua/controller/AdvisorController.java
package org.mua.controller;

import org.mua.model.Advisor;
import org.mua.model.Major;
import org.mua.service.AdvisorService;
import org.mua.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/advisors")
@CrossOrigin(origins = "*")  // 允许跨域请求
public class AdvisorController {

    @Autowired
    private AdvisorService advisorService;

    @Autowired
    private MajorService majorService;

    @PostMapping("/create")
    public ResponseEntity<Advisor> createAdvisor(@RequestBody Advisor advisor) {
        try {
            Advisor createdAdvisor = advisorService.createAdvisor(advisor);
            return ResponseEntity.ok(createdAdvisor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advisor> getAdvisorById(@PathVariable Long id) {
        return advisorService.getAdvisorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Advisor> getAllAdvisors() {
        return advisorService.getAllAdvisors();
    }

    @GetMapping("/major/{majorId}")
    public ResponseEntity<List<Advisor>> getAdvisorsByMajor(@PathVariable Long majorId) {
        Optional<Major> majorOpt = majorService.getMajorById(majorId);
        if (majorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Advisor> advisors = advisorService.getAdvisorsByMajor(majorOpt.get());
        return ResponseEntity.ok(advisors);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Advisor> updateAdvisor(@PathVariable Long id, @RequestBody Advisor advisorDetails) {
        try {
            Advisor updatedAdvisor = advisorService.updateAdvisor(id, advisorDetails);
            return ResponseEntity.ok(updatedAdvisor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdvisor(@PathVariable Long id) {
        try {
            advisorService.deleteAdvisor(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
