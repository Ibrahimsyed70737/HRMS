package com.hrms.hrms_backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.hrms_backend.dto.LeaveRequestCreateRequest;
import com.hrms.hrms_backend.dto.LeaveRequestDTO;
import com.hrms.hrms_backend.entity.LeaveRequest;
import com.hrms.hrms_backend.mapper.LeaveRequestMapper;
import com.hrms.hrms_backend.service.LeaveRequestService;
import com.hrms.hrms_backend.security.SecurityUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    private final SecurityUtils securityUtils;
    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService, SecurityUtils securityUtils) {
        this.leaveRequestService = leaveRequestService;
        this.securityUtils = securityUtils;
    }

    // Note: employeeId is a query param for now (no auth yet — Phase 6 will replace this
    // with "get current logged-in user" instead of trusting a client-passed ID).
    @PostMapping
public ResponseEntity<LeaveRequestDTO> apply(@Valid @RequestBody LeaveRequestCreateRequest request) {
    Long employeeId = securityUtils.getCurrentEmployeeId(); // no longer from query param
    LeaveRequest leaveRequest = new LeaveRequest();
    leaveRequest.setLeaveType(request.getLeaveType());
    leaveRequest.setStartDate(request.getStartDate());
    leaveRequest.setEndDate(request.getEndDate());
    LeaveRequest saved = leaveRequestService.apply(leaveRequest, employeeId);
    return ResponseEntity.status(HttpStatus.CREATED).body(LeaveRequestMapper.toDTO(saved));
}

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(LeaveRequestMapper.toDTO(leaveRequestService.getById(id)));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestDTO>> getByEmployee(@PathVariable Long employeeId) {
        List<LeaveRequestDTO> requests = leaveRequestService.getByEmployee(employeeId).stream()
                .map(LeaveRequestMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LeaveRequestDTO>> getPending() {
        List<LeaveRequestDTO> requests = leaveRequestService.getPending().stream()
                .map(LeaveRequestMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{id}/approve")
public ResponseEntity<LeaveRequestDTO> approve(@PathVariable Long id) {
    Long approverId = securityUtils.getCurrentEmployeeId();
    return ResponseEntity.ok(LeaveRequestMapper.toDTO(leaveRequestService.approve(id, approverId)));
}

@PutMapping("/{id}/reject")
public ResponseEntity<LeaveRequestDTO> reject(@PathVariable Long id) {
    Long approverId = securityUtils.getCurrentEmployeeId();
    return ResponseEntity.ok(LeaveRequestMapper.toDTO(leaveRequestService.reject(id, approverId)));
}
}