package com.akash.job_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JobApplicationRequestDTO {

	@NotBlank(message = "Company name is required")
	@Size(max = 150, message = "Company name cannot exceed 150 characters")
	private String companyName;

	@NotBlank(message = "Job title is required")
	@Size(max = 150, message = "Job title cannot exceed 150 characters")
	private String jobTitle;

	@NotNull(message = "Application date is required")
	private LocalDate applicationDate;

	@Size(max = 30, message = "Status cannot exceed 30 characters")
	private String status;

	@Size(max = 30, message = "Employment type cannot exceed 30 characters")
	private String employmentType;

	@Size(max = 150, message = "Location cannot exceed 150 characters")
	private String location;

	@Size(max = 1000, message = "Job URL cannot exceed 1000 characters")
	private String jobUrl;

	@DecimalMin(value = "0.0", message = "Minimum salary cannot be negative")
	private BigDecimal salaryMin;

	@DecimalMin(value = "0.0", message = "Maximum salary cannot be negative")
	private BigDecimal salaryMax;

	@Size(min = 3, max = 3, message = "Currency code must contain exactly 3 characters")
	private String currencyCode;

	private String notes;

    public JobApplicationRequestDTO() {
    }

    // Getters and Setters

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(BigDecimal salaryMin) {
        this.salaryMin = salaryMin;
    }

    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(BigDecimal salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}