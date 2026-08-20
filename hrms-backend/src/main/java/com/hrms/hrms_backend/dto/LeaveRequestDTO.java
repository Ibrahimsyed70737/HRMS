package com.hrms.hrms_backend.dto;

import java.time.LocalDate;

import com.hrms.hrms_backend.entity.LeaveStatus;
import com.hrms.hrms_backend.entity.LeaveType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequestDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;
    private LocalDate appliedOn;
    private Long approvedById;
    private String approvedByName;
}