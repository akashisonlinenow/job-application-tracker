
package com.akash.job_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.job_tracker.dto.LoginRequestDTO;
import com.akash.job_tracker.dto.LoginResponseDTO;
import com.akash.job_tracker.service.AuthenticationService;

import jakarta.validation.Valid;

/**
 * REST controller that exposes user authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Creates the authentication controller.
     *
     * @param authenticationService service responsible for user login
     */
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request validated login credentials
     * @return HTTP 200 with the JWT and user details
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        LoginResponseDTO response = authenticationService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
