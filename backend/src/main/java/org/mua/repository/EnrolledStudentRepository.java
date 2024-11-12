// src/main/java/org/mua/repository/EnrolledStudentRepository.java
package org.mua.repository;

import org.mua.model.EnrolledStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnrolledStudentRepository extends JpaRepository<EnrolledStudent, String> {
    Optional<EnrolledStudent> findByUsernameAndIdNumber(String username, String idNumber);
}
