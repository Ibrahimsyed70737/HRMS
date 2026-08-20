package com.hrms.hrms_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.hrms_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring parses this method name and generates:
    // SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}