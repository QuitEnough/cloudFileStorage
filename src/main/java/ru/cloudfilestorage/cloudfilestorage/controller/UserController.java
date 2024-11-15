package ru.cloudfilestorage.cloudfilestorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.repository.UserRepository;
import ru.cloudfilestorage.cloudfilestorage.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserServiceImpl userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Get User by Id or Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Authorization is required"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Bad request")
    })
    public Object getUserByIdOrEmail(@RequestParam(required = false) Long id,
                                     @RequestParam(required = false) String email) {
        if (id != null) {
            return userService.getUserById(id);
        }
        if (email != null) {
            return userService.getUserByEmail(email);
        }

        return List.of();
    }

    @GetMapping("/{value}")
    @Operation(summary = "Get User by Id or Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Authorization is required"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Bad request")
    })
    public List<?> getUserByValue(@PathVariable String value) {
        return List.of(userService.getUserByValue(value));
    }

    @GetMapping(value = "/byEmail/{email}", produces = "application/json")
    @Operation(summary = "Get User by Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Authorization is required"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Bad request")
    })
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get User by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Authorization is required"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Bad request")
    })
    public UserResponse getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

}
