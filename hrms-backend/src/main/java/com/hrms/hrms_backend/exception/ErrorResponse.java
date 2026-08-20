package com.hrms.hrms_backend.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors; // only populated for validation failures, null otherwise
}