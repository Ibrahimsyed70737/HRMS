package com.hrms.hrms_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrms.hrms_backend.entity.Employee;
import com.hrms.hrms_backend.entity.LeaveRequest;
import com.hrms.hrms_backend.entity.LeaveStatus;
import com.hrms.hrms_backend.exception.ResourceNotFoundException;
import com.hrms.hrms_backend.repository.EmployeeRepository;
import com.hrms.hrms_backend.repository.LeaveRequestRepository;
import com.hrms.hrms_backend.service.LeaveRequestService;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository,
                                    EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public LeaveRequest apply(LeaveRequest leaveRequest, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        if (leaveRequest.getEndDate().isBefore(leaveRequest.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(LeaveStatus.PENDING); // enforce — never trust client-sent status
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest getById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));
    }

    @Override
    public List<LeaveRequest> getByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<LeaveRequest> getPending() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING);
    }

    @Override
    public LeaveRequest approve(Long leaveRequestId, Long approverId) {
        LeaveRequest leaveRequest = getById(leaveRequestId);
        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: " + approverId));

        validateApproval(leaveRequest, approver);

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setApprovedBy(approver);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest reject(Long leaveRequestId, Long approverId) {
        LeaveRequest leaveRequest = getById(leaveRequestId);
        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: " + approverId));

        validateApproval(leaveRequest, approver);

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setApprovedBy(approver);
        return leaveRequestRepository.save(leaveRequest);
    }

    // Shared validation for both approve() and reject() — this is where the real business rules live.
    private void validateApproval(LeaveRequest leaveRequest, Employee approver) {
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                "Leave request has already been " + leaveRequest.getStatus() + " — cannot act on it again");
        }

        if (leaveRequest.getEmployee().getId().equals(approver.getId())) {
            throw new IllegalStateException("You cannot approve or reject your own leave request");
        }
    }
}