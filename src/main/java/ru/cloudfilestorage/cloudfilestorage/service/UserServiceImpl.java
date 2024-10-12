package ru.cloudfilestorage.cloudfilestorage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.entity.User;
import ru.cloudfilestorage.cloudfilestorage.exception.UserServiceCustomException;
import ru.cloudfilestorage.cloudfilestorage.mapper.UserMapper;
import ru.cloudfilestorage.cloudfilestorage.model.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.model.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.repository.UserRepository;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public long addUser(UserRequest userRequest) {
        log.info("Adding user ...");

        User user = userMapper.toUser(userRequest);

        userRepository.save(user);

        log.info("User created");

        return user.getId();
    }

    @Override
    public UserResponse getUserById(long userId) {
        log.info("Get user for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserServiceCustomException(
                                "User with given id not found",
                                "USER_NOT_FOUND"
                        )
                );

        UserResponse userResponse = userMapper.toUserResponse(user);

        log.info("[Service] get user data returning for: {}", userResponse);
        return userResponse;
    }

}
