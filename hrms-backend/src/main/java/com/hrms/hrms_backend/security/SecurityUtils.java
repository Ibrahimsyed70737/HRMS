package com.hrms.hrms_backend.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hrms.hrms_backend.entity.User;
import com.hrms.hrms_backend.repository.UserRepository;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Reads the username Spring Security already verified via JWT, then loads the full User.
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in DB"));
    }

    public Long getCurrentEmployeeId() {
        User user = getCurrentUser();
        if (user.getEmployee() == null) {
            throw new IllegalStateException("This account is not linked to an employee record");
        }
        return user.getEmployee().getId();
    }
}