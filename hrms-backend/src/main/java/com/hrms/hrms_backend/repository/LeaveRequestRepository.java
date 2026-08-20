package com.hrms.hrms_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.hrms_backend.entity.LeaveRequest;
import com.hrms.hrms_backend.entity.LeaveStatus;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // All leave requests by a specific employee
    List<LeaveRequest> findByEmployeeId(Long employeeId);

    // All leave requests with a given status — useful for a manager's "pending approvals" screen
    List<LeaveRequest> findByStatus(LeaveStatus status);

    // Combine both: pending requests for a specific employee
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);
}