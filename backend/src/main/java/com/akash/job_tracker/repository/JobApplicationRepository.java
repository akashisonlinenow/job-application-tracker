package com.akash.job_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.job_tracker.entity.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>{
	List<JobApplication> findByUserId(Long id);
	List<JobApplication> findByUserIdAndStatus(Long id, String status);
}
