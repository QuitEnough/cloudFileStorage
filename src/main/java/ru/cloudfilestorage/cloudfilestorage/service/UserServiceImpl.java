package ru.cloudfilestorage.cloudfilestorage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.entity.User;
import ru.cloudfilestorage.cloudfilestorage.exception.UserServiceCustomException;
import ru.cloudfilestorage.cloudfilestorage.mapper.UserMapper;
import ru.cloudfilestorage.cloudfilestorage.dto.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.dto.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.repository.UserRepository;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public long addUser(UserRequest userRequest) {

        User user = userMapper.toUser(userRequest);

        userRepository.save(user);

        return user.getId();
    }

    @Override
    public UserResponse getUserById(long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserServiceCustomException("User with given id not found")
                );

        UserResponse userResponse = userMapper.toUserResponse(user);

        return userResponse;
    }

}
