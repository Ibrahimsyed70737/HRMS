package com.hrms.hrms_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private String designation;
    private LocalDate dateOfJoining;
    private Long departmentId;
    private String departmentName; // flattened, not a nested object — avoids recursion
    private Long managerId;
    private String managerName;
}