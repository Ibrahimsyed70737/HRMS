package com.hrms.hrms_backend.mapper;

import com.hrms.hrms_backend.dto.LeaveRequestDTO;
import com.hrms.hrms_backend.entity.LeaveRequest;

public class LeaveRequestMapper {

    public static LeaveRequestDTO toDTO(LeaveRequest leaveRequest) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setStatus(leaveRequest.getStatus());
        dto.setAppliedOn(leaveRequest.getAppliedOn());

        if (leaveRequest.getEmployee() != null) {
            dto.setEmployeeId(leaveRequest.getEmployee().getId());
            dto.setEmployeeName(leaveRequest.getEmployee().getName());
        }
        if (leaveRequest.getApprovedBy() != null) {
            dto.setApprovedById(leaveRequest.getApprovedBy().getId());
            dto.setApprovedByName(leaveRequest.getApprovedBy().getName());
        }
        return dto;
    }
}