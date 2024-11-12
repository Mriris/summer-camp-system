package org.mua.repository;

import org.mua.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // 新增：通过邮箱查找用户
    Optional<User> findByIdNumber(String idNumber); // 新增：通过身份证号查找用户
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByIdNumber(String idNumber);
}
