package ru.cloudfilestorage.cloudfilestorage.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.UserDetailsImpl;
import ru.cloudfilestorage.cloudfilestorage.service.UserService;

@Component("cse")
public class CustomSecurityExpression {

    private final UserService userService;

    public CustomSecurityExpression(UserService userService) {
        this.userService = userService;
    }

    public boolean canAccessFile(final Long fileId) {
        UserDetailsImpl user = getPrincipal();
        Long id = user.getId();

        return userService.isFileOwner(id, fileId);
    }

    private UserDetailsImpl getPrincipal() {
        Authentication authentication = SecurityContextHolder
                                            .getContext()
                                            .getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

}
