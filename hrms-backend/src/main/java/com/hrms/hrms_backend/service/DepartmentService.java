package com.hrms.hrms_backend.service;

import java.util.List;

import com.hrms.hrms_backend.entity.Department;

public interface DepartmentService {
    Department create(Department department);
    Department getById(Long id);
    List<Department> getAll();
    Department update(Long id, Department updatedDepartment);
    void delete(Long id);
}