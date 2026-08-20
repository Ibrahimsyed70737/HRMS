package com.hrms.hrms_backend.mapper;

import com.hrms.hrms_backend.dto.DepartmentDTO;
import com.hrms.hrms_backend.entity.Department;

public class DepartmentMapper {

    public static DepartmentDTO toDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }
}