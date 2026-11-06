package com.akash.job_tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.akash.job_tracker.dto.JobApplicationRequestDTO;
import com.akash.job_tracker.dto.JobApplicationResponseDTO;
import com.akash.job_tracker.service.JobApplicationService;

import jakarta.validation.Valid;

/**
 * Handles REST API requests for creating, retrieving,
 * updating, and deleting job applications.
 *
 * Each operation uses the authenticated user's ID
 * obtained from Spring Security.
 */
@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    /**
     * Creates the controller with its required service dependency.
     *
     * @param jobApplicationService service for managing job applications
     */
    public JobApplicationController(
            JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    /**
     * Extracts the authenticated user's database ID.
     *
     * @param authentication current Spring Security authentication
     * @return ID of the authenticated user
     */
    private Long getAuthenticatedUserId(Authentication authentication) {
        return (Long) authentication.getPrincipal();
    }

    /**
     * Creates a new job application for the authenticated user.
     *
     * @param request job application details received from the frontend
     * @param authentication current authenticated user's information
     * @return created job application with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> createApplication(
            @Valid @RequestBody JobApplicationRequestDTO request,
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        JobApplicationResponseDTO response =
                jobApplicationService.createApplication(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all job applications belonging to the authenticated user.
     *
     * @param authentication current authenticated user's information
     * @return list of the user's job applications
     */
    @GetMapping
    public ResponseEntity<List<JobApplicationResponseDTO>> getAllApplications(
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                jobApplicationService.getAllApplications(userId));
    }

    /**
     * Retrieves a specific job application owned by the authenticated user.
     *
     * @param id ID of the requested job application
     * @param authentication current authenticated user's information
     * @return requested job application
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> getApplicationById(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                jobApplicationService.getApplicationById(userId, id));
    }

    /**
     * Updates a job application belonging to the authenticated user.
     *
     * @param id ID of the job application to update
     * @param request updated job application details
     * @param authentication current authenticated user's information
     * @return updated job application
     */
    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequestDTO request,
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                jobApplicationService.updateApplication(userId, id, request));
    }

    /**
     * Deletes a job application belonging to the authenticated user.
     *
     * @param id ID of the job application to delete
     * @param authentication current authenticated user's information
     * @return empty response with HTTP 204 status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        jobApplicationService.deleteApplication(userId, id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Handles requests for job applications that cannot be found
     * or do not belong to the authenticated user.
     *
     * @param exception exception containing the error message
     * @return error response with HTTP 404 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(
            IllegalArgumentException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }
}