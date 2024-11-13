// src/main/java/org/mua/repository/AdvisorRepository.java
package org.mua.repository;

import org.mua.model.Advisor;
import org.mua.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
    List<Advisor> findByMajor(Major major);
    boolean existsByEmail(String email);

    boolean existsByIdAndMajorId(Long advisorId, Long majorId);
}
