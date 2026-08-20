package com.hrms.hrms_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.hrms_backend.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find all employees in a given department (uses the department's id)
    List<Employee> findByDepartmentId(Long departmentId);

    // Find all employees reporting to a given manager
    List<Employee> findByManagerId(Long managerId);

    Optional<Employee> findByEmail(String email);
}