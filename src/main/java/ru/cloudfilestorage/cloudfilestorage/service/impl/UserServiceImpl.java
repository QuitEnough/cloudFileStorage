package ru.cloudfilestorage.cloudfilestorage.service.impl;

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

@Service
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
        User user = userMapper.toUser(request);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with given id not found"));

        return userMapper.toUserResponse(user);
    }

    @Override
    public boolean isFileOwner(Long userId, Long fileId) {
        return userRepository.isFileOwner(userId, fileId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with given email is not found"));

        return userMapper.toUserDetails(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with given email is not found"));
    }
}
