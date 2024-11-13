// src/main/java/org/mua/repository/MajorRepository.java
package org.mua.repository;

import org.mua.model.Major;
import org.mua.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByCollege(College college);
    boolean existsByName(String name);

    boolean existsByIdAndCollegeId(Long majorId, Long collegeId);
}
