package ru.cloudfilestorage.cloudfilestorage.service;

import ru.cloudfilestorage.cloudfilestorage.dto.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.dto.UserResponse;

public interface UserService {

    long addUser(UserRequest userRequest);
    UserResponse getUserById(long userId);

}
