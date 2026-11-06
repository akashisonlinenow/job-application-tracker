package com.akash.job_tracker.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.job_tracker.dto.LoginRequestDTO;
import com.akash.job_tracker.dto.LoginResponseDTO;
import com.akash.job_tracker.entity.AppUser;
import com.akash.job_tracker.repository.AppUserRepository;
import com.akash.job_tracker.security.JwtService;

/**
 * Handles user authentication and JWT token generation.
 */
@Service
@Transactional(readOnly = true)
public class AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Creates the authentication service with its required dependencies.
     *
     * @param appUserRepository repository used to retrieve registered users
     * @param passwordEncoder encoder used to verify stored password hashes
     * @param jwtService service used to generate JWT tokens
     */
    public AuthenticationService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Authenticates a user and generates a JWT after successful verification.
     *
     * @param request login email and raw password
     * @return JWT token and basic authenticated user information
     * @throws BadCredentialsException if the email or password is incorrect
     */
    public LoginResponseDTO login(LoginRequestDTO request) {

        AppUser user = appUserRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(
                token,
                "Bearer",
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
