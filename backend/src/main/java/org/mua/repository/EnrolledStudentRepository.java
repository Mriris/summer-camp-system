package org.mua.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.mua.model.EnrolledStudent;

@Repository
public interface EnrolledStudentRepository extends JpaRepository<EnrolledStudent, String> {
    boolean existsByUsernameAndIdNumber(String username, String idNumber);
}
