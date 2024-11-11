package ru.cloudfilestorage.cloudfilestorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.AuthenticationRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.JwtAuthenticationResponse;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserCreateRequest;
import ru.cloudfilestorage.cloudfilestorage.service.JwtService;
import ru.cloudfilestorage.cloudfilestorage.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/public")
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService tokenService;

    private final UserServiceImpl userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtService tokenService,
                          UserServiceImpl userService) {

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign up the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "User null or invalid values"),
            @ApiResponse(responseCode = "500", description = "User duplicated values")
    })
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserCreateRequest request) {
        userService.addUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/auth")
    @Operation(summary = "Authorization for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authorised"),
            @ApiResponse(responseCode = "400", description = "User invalid password or login")
    })
    public JwtAuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        return new JwtAuthenticationResponse(tokenService.generateToken(user));
    }

}
