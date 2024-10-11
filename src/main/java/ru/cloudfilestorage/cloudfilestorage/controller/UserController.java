package ru.cloudfilestorage.cloudfilestorage.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cloudfilestorage.cloudfilestorage.model.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.model.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.service.UserServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.validation.ValidUserId;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody UserRequest userRequest) {
        long userId = userService.addUser(userRequest);
        log.info("[Response] user added with data: {}", userRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long userId) {// @ValidUserId long userId) {
        log.info("[RequestParams] get user data for userId: {}", userId);
        UserResponse userResponse = userService.getUserById(userId);
        log.info("[Response] get user data {}", userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
