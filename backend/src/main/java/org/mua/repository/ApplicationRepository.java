// src/main/java/org/mua/repository/ApplicationRepository.java
package org.mua.repository;

import org.mua.model.Application;
import org.mua.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
}
