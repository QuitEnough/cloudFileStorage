package ru.cloudfilestorage.cloudfilestorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cloudfilestorage.cloudfilestorage.model.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.model.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody UserRequest userRequest) {
        long userId = userService.addUser(userRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserResponse> getUserByUserName(@PathVariable String userName) {
        UserResponse userResponse = userService.getUserByUserName(userName);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
