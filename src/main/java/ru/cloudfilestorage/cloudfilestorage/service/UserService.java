package ru.cloudfilestorage.cloudfilestorage.service;

import ru.cloudfilestorage.cloudfilestorage.entity.User;
import ru.cloudfilestorage.cloudfilestorage.model.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.model.UserResponse;

public interface UserService {

    long addUser(UserRequest userRequest);
    UserResponse getUserById(long userId);

}
