package ru.cloudfilestorage.cloudfilestorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.JwtAuthenticationResponse;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.SignInRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.SignUpRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Role;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;
import ru.cloudfilestorage.cloudfilestorage.domain.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationManager authorizationManager;
    private final AuthorizationManager authorizationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        UserRequest userRequest = userMapper.toUserRequest(user);
        if (!userService.isExists(userRequest)){
            userService.addUser(userRequest);
        }

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        /*authorizationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));*/

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

}
