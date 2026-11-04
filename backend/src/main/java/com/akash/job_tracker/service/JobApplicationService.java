package com.akash.job_tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.job_tracker.dto.JobApplicationRequestDTO;
import com.akash.job_tracker.dto.JobApplicationResponseDTO;
import com.akash.job_tracker.entity.AppUser;
import com.akash.job_tracker.entity.JobApplication;
import com.akash.job_tracker.repository.AppUserRepository;
import com.akash.job_tracker.repository.JobApplicationRepository;

@Service
@Transactional
public class JobApplicationService {

	private final JobApplicationRepository jobApplicationRepository;
	private final AppUserRepository appUserRepository;

	public JobApplicationService(JobApplicationRepository jobApplicationRepository,
			AppUserRepository appUserRepository) {

		this.jobApplicationRepository = jobApplicationRepository;
		this.appUserRepository = appUserRepository;
	}

	// Create a new job application
	public JobApplicationResponseDTO createApplication(Long userId, JobApplicationRequestDTO request) {

		AppUser user = getUser(userId);

		JobApplication application = new JobApplication();

		application.setUser(user);
		application.setCompanyName(request.getCompanyName());
		application.setJobTitle(request.getJobTitle());
		application.setApplicationDate(request.getApplicationDate());
		application.setStatus(request.getStatus() == null ? "APPLIED" : request.getStatus());
		application.setEmploymentType(request.getEmploymentType());
		application.setLocation(request.getLocation());
		application.setJobUrl(request.getJobUrl());
		application.setSalaryMin(request.getSalaryMin());
		application.setSalaryMax(request.getSalaryMax());
		application.setCurrencyCode(request.getCurrencyCode() == null ? "INR" : request.getCurrencyCode());
		application.setNotes(request.getNotes());

		JobApplication savedApplication = jobApplicationRepository.save(application);

		return convertToDTO(savedApplication);
	}

	// Get all applications belonging to a user
	@Transactional(readOnly = true)
	public List<JobApplicationResponseDTO> getAllApplications(Long userId) {

		return jobApplicationRepository.findByUserId(userId).stream().map(this::convertToDTO).toList();
	}

	// Get one application belonging to a user
	@Transactional(readOnly = true)
	public JobApplicationResponseDTO getApplicationById(Long userId, Long applicationId) {

		JobApplication application = getUserApplication(userId, applicationId);

		return convertToDTO(application);
	}

	// Update an application
	public JobApplicationResponseDTO updateApplication(Long userId, Long applicationId,
			JobApplicationRequestDTO request) {

		JobApplication application = getUserApplication(userId, applicationId);

		application.setCompanyName(request.getCompanyName());
		application.setJobTitle(request.getJobTitle());
		application.setApplicationDate(request.getApplicationDate());
		application.setStatus(request.getStatus());
		application.setEmploymentType(request.getEmploymentType());
		application.setLocation(request.getLocation());
		application.setJobUrl(request.getJobUrl());
		application.setSalaryMin(request.getSalaryMin());
		application.setSalaryMax(request.getSalaryMax());
		application.setCurrencyCode(request.getCurrencyCode());
		application.setNotes(request.getNotes());

		JobApplication updatedApplication = jobApplicationRepository.save(application);

		return convertToDTO(updatedApplication);
	}

	// Delete an application
	public void deleteApplication(Long userId, Long applicationId) {

		JobApplication application = getUserApplication(userId, applicationId);

		jobApplicationRepository.delete(application);
	}

	// Find user
	private AppUser getUser(Long userId) {

		return appUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
	}

	// Find application only if it belongs to the requested user
	private JobApplication getUserApplication(Long userId, Long applicationId) {

		return jobApplicationRepository.findById(applicationId)
				.filter(application -> application.getUser().getId().equals(userId))
				.orElseThrow(() -> new IllegalArgumentException("Job application not found"));
	}

	// Convert entity to response DTO
	private JobApplicationResponseDTO convertToDTO(JobApplication application) {

		JobApplicationResponseDTO dto = new JobApplicationResponseDTO();

		dto.setId(application.getId());
		dto.setCompanyName(application.getCompanyName());
		dto.setJobTitle(application.getJobTitle());
		dto.setApplicationDate(application.getApplicationDate());
		dto.setStatus(application.getStatus());
		dto.setEmploymentType(application.getEmploymentType());
		dto.setLocation(application.getLocation());
		dto.setJobUrl(application.getJobUrl());
		dto.setSalaryMin(application.getSalaryMin());
		dto.setSalaryMax(application.getSalaryMax());
		dto.setCurrencyCode(application.getCurrencyCode());
		dto.setNotes(application.getNotes());
		dto.setCreatedAt(application.getCreatedAt());
		dto.setUpdatedAt(application.getUpdatedAt());

		return dto;
	}
}