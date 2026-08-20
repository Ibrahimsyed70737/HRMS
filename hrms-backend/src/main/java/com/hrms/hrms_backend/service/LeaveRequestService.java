package com.hrms.hrms_backend.service;

import java.util.List;

import com.hrms.hrms_backend.entity.LeaveRequest;

public interface LeaveRequestService {
    LeaveRequest apply(LeaveRequest leaveRequest, Long employeeId);
    LeaveRequest getById(Long id);
    List<LeaveRequest> getByEmployee(Long employeeId);
    List<LeaveRequest> getPending();
    LeaveRequest approve(Long leaveRequestId, Long approverId);
    LeaveRequest reject(Long leaveRequestId, Long approverId);
}