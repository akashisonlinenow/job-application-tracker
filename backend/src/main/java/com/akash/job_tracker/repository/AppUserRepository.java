package com.akash.job_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.job_tracker.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByEmail(String email);
	boolean existsByEmail(String email);
}
