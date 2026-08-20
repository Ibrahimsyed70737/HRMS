package com.hrms.hrms_backend.dto;

import lombok.Getter;
import lombok.Setter;

// Separate "create" DTO — only fields the client should be able to SEND.
// Notice: no 'id' field. The client never gets to choose an ID.
@Getter
@Setter
public class EmployeeCreateRequest {
    private String name;
    private String email;
    private String designation;
    private Long departmentId;
    private Long managerId; // optional
}