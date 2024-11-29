package ru.cloudfilestorage.cloudfilestorage.controller;

import org.junit.jupiter.api.Test;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    private final UserController userController = new UserController(userService);

    @Test
    void testGetUserByIdOrEmail_success() {

        UserResponse userResponse = UserResponse
                .builder()
                .userId(1L)
                .email("mail@mail.ru")
                .build();

        when(userService.getUserById(1L)).thenReturn(userResponse);

        var user = userController.getUserByIdOrEmail(1L, "mail@mail.ru");

        assertNotNull(user);

    }

}