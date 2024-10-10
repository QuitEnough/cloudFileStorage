package ru.cloudfilestorage.cloudfilestorage.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.entity.User;
import ru.cloudfilestorage.cloudfilestorage.exception.UserServiceCustomException;
import ru.cloudfilestorage.cloudfilestorage.model.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.model.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.repository.UserRepository;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long addUser(UserRequest userRequest) {
        log.info("Adding user ...");

        User user = User.builder()
                .userName(userRequest.getUserName())
                .password(userRequest.getPassword())
                .build();

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

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        return userResponse;
    }

    @Override
    public UserResponse getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new UserServiceCustomException(
                                "User with given user_name not found",
                                "USER_NOT_FOUND"
                        )
                );
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        return userResponse;
    }
}
