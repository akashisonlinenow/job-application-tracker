package com.akash.job_tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.akash.job_tracker.dto.UserRegistrationDTO;
import com.akash.job_tracker.dto.UserResponseDTO;
import com.akash.job_tracker.entity.AppUser;
import com.akash.job_tracker.repository.AppUserRepository;

@Service
public class UserService {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {

		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponseDTO registerUser(UserRegistrationDTO request) {

		// Check whether email already exists
		if (appUserRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email is already registered");
		}

		// Create a new user
		AppUser user = new AppUser();

		user.setName(request.getName());
		user.setEmail(request.getEmail());

		// Hash password before saving
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

		// Save user in MySQL
		AppUser savedUser = appUserRepository.save(user);

		// Return safe user information
		return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
	}
}