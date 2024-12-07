package ru.cloudfilestorage.cloudfilestorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cloudfilestorage.cloudfilestorage.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "User API")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
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
        log.debug("[RequestParams] get User with id {} or email {}", id, email);
        if (id != null) {
            return userService.getUserById(id);
        }
        if (email != null) {
            return userService.getUserByEmail(email);
        }

        return List.of();
    }

}
