package ru.cloudfilestorage.cloudfilestorage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserCreateRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Role;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;
import ru.cloudfilestorage.cloudfilestorage.domain.mapper.UserMapper;
import ru.cloudfilestorage.cloudfilestorage.exception.UserNotFoundException;
import ru.cloudfilestorage.cloudfilestorage.repository.UserRepository;
import ru.cloudfilestorage.cloudfilestorage.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    @Override
    public void addUser(UserCreateRequest request) {

        log.debug("[UserService] Saving user with Details {}", request);

        User user = userMapper.toUser(request);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(long userId) {

        log.debug("[UserService] Getting user Details with id {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with given id not found"));

        return userMapper.toUserResponse(user);
    }

    @Override
    public boolean isFileOwner(Long userId, Long fileId) {
        log.debug("[UserService] Checking if user with id {} is owner of file with id {}", userId, fileId);
        return userRepository.isFileOwner(userId, fileId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.debug("[UserService] Getting user Details with email {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with given email is not found"));

        return userMapper.toUserDetails(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {

        log.debug("[UserService] Getting User with email {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with given email is not found"));

        return userMapper.toUserResponse(user);
    }

}
