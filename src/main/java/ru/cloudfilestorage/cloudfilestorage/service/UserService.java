package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserCreateRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;

import java.util.List;

public interface UserService extends UserDetailsService {

    void addUser(UserCreateRequest request);

    UserResponse getUserById(long userId);

    UserResponse getUserByEmail(String email);

    boolean isFileOwner(Long userId, Long fileId);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
