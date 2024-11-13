// src/main/java/org/mua/repository/CollegeRepository.java
package org.mua.repository;

import org.mua.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    boolean existsByName(String name);
}
