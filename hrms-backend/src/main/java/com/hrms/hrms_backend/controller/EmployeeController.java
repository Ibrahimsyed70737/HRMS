package com.hrms.hrms_backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.hrms_backend.dto.EmployeeCreateRequest;
import com.hrms.hrms_backend.dto.EmployeeDTO;
import com.hrms.hrms_backend.entity.Employee;
import com.hrms.hrms_backend.mapper.EmployeeMapper;
import com.hrms.hrms_backend.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeCreateRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDesignation(request.getDesignation());

        Employee saved = employeeService.create(employee, request.getDepartmentId(), request.getManagerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(EmployeeMapper.toDTO(employeeService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> employees = employeeService.getAll().stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> getByDepartment(@PathVariable Long departmentId) {
        List<EmployeeDTO> employees = employeeService.getByDepartment(departmentId).stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeCreateRequest request) {
        Employee updated = new Employee();
        updated.setName(request.getName());
        updated.setDesignation(request.getDesignation());
        Employee saved = employeeService.update(id, updated);
        return ResponseEntity.ok(EmployeeMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}