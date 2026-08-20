package com.hrms.hrms_backend.service;

import java.util.List;

import com.hrms.hrms_backend.entity.Employee;

public interface EmployeeService {
    Employee create(Employee employee, Long departmentId, Long managerId);
    Employee getById(Long id);
    List<Employee> getAll();
    List<Employee> getByDepartment(Long departmentId);
    Employee update(Long id, Employee updatedEmployee);
    void delete(Long id);
}