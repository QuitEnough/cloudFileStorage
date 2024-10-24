package ru.cloudfilestorage.cloudfilestorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;
import ru.cloudfilestorage.cloudfilestorage.exception.UserServiceCustomException;
import ru.cloudfilestorage.cloudfilestorage.domain.mapper.UserMapper;
import ru.cloudfilestorage.cloudfilestorage.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

    @Override
    public boolean isExists(UserRequest userRequest) {
        return userRepository.existsByEmail(userRequest.getEmail());
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UserServiceCustomException("User with given email is not found")
                );
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByEmail;
    }


}
