package com.hrms.hrms_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrms.hrms_backend.entity.Department;
import com.hrms.hrms_backend.entity.Employee;
import com.hrms.hrms_backend.exception.ResourceNotFoundException;
import com.hrms.hrms_backend.repository.DepartmentRepository;
import com.hrms.hrms_backend.repository.EmployeeRepository;
import com.hrms.hrms_backend.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                                DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Employee create(Employee employee, Long departmentId, Long managerId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        employee.setDepartment(department);

        if (managerId != null) {
            Employee manager = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + managerId));
            employee.setManager(manager);
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    @Override
    public Employee update(Long id, Employee updatedEmployee) {
        Employee existing = getById(id);
        existing.setName(updatedEmployee.getName());
        existing.setDesignation(updatedEmployee.getDesignation());
        // Deliberately NOT updating email/department/manager here —
        // those are sensitive changes we'll expose as separate, explicit endpoints later
        // rather than silently allowing them through a generic "update" call.
        return employeeRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Employee existing = getById(id);
        employeeRepository.delete(existing);
    }
}