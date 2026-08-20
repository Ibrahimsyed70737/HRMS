package com.hrms.hrms_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrms.hrms_backend.entity.Department;
import com.hrms.hrms_backend.exception.ResourceNotFoundException;
import com.hrms.hrms_backend.repository.DepartmentRepository;
import com.hrms.hrms_backend.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department create(Department department) {
        if (departmentRepository.findByName(department.getName()).isPresent()) {
            throw new IllegalArgumentException("Department with this name already exists");
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department update(Long id, Department updatedDepartment) {
        Department existing = getById(id); // reuses getById — DRY, and gives us the not-found check for free
        existing.setName(updatedDepartment.getName());
        return departmentRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Department existing = getById(id);
        departmentRepository.delete(existing);
    }
}