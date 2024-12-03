package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Role;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.UserDetailsImpl;
import ru.cloudfilestorage.cloudfilestorage.domain.mapper.UserMapper;
import ru.cloudfilestorage.cloudfilestorage.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserMapper userMapper = mock(UserMapper.class);

    private final PasswordEncoder encoder = mock(PasswordEncoder.class);

    private final UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, encoder);

    @Test
    void getUserByIdSuccess() {
        User user = User
                .builder()
                .id(1L)
                .email("mail@mail.ru")
                .password("Pash4ka")
                .role(Role.USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    void userIsFileOwnerSuccess() {
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(UUID.randomUUID())
                .directoryId(1L)
                .userId(1L)
                .build();

        when(userRepository.isFileOwner(file.getUserId(), file.getId())).thenReturn(Boolean.TRUE);
    }

    @Test
    void userLoadedByUsernameSuccess() {
        User user = User
                .builder()
                .id(1L)
                .email("mail@mail.ru")
                .password("Pash4ka")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetailsImpl userDetails = UserDetailsImpl
                .builder()
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        when(userMapper.toUserDetails(user)).thenReturn(userDetails);
    }

    @Test
    void userGotByEmailSuccess() {
        User user = User
                .builder()
                .id(1L)
                .email("mail@mail.ru")
                .password("Pash4ka")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserResponse userResponse = UserResponse
                .builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();

        when(userMapper.toUserResponse(user)).thenReturn(userResponse);
    }

}