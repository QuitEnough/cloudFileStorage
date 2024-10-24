package ru.cloudfilestorage.cloudfilestorage.service;

import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;

public interface UserService {

    long addUser(UserRequest userRequest);
    UserResponse getUserById(long userId);
    boolean isExists(UserRequest userRequest);

}
