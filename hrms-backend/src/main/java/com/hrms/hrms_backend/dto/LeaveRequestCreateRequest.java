package com.hrms.hrms_backend.dto;

import java.time.LocalDate;

import com.hrms.hrms_backend.entity.LeaveType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequestCreateRequest {
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    // Notice: no 'status' field here either — client can never set status directly.
}