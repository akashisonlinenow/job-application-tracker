package com.akash.job_tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akash.job_tracker.dto.JobApplicationRequestDTO;
import com.akash.job_tracker.dto.JobApplicationResponseDTO;
import com.akash.job_tracker.service.JobApplicationService;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    // TEMPORARY: Replace with authenticated user ID later
    private static final Long TEST_USER_ID = 1L;

    public JobApplicationController(
            JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // Create application
    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> createApplication(
            @RequestBody JobApplicationRequestDTO request) {

        JobApplicationResponseDTO response =
                jobApplicationService.createApplication(TEST_USER_ID, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all applications
    @GetMapping
    public ResponseEntity<List<JobApplicationResponseDTO>> getAllApplications() {

        return ResponseEntity.ok(
                jobApplicationService.getAllApplications(TEST_USER_ID));
    }

    // Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> getApplicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobApplicationService.getApplicationById(TEST_USER_ID, id));
    }

    // Update application
    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> updateApplication(
            @PathVariable Long id,
            @RequestBody JobApplicationRequestDTO request) {

        return ResponseEntity.ok(
                jobApplicationService.updateApplication(
                        TEST_USER_ID, id, request));
    }

    // Delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {

        jobApplicationService.deleteApplication(TEST_USER_ID, id);

        return ResponseEntity.noContent().build();
    }

    // Handle application not found
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(
            IllegalArgumentException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }
}