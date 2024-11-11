package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.UserDetailsImpl;

@Component("UserAccessor")
public class CustomSecurityExpression {

    private final UserService userService;

    @Autowired
    public CustomSecurityExpression(UserService userService) {
        this.userService = userService;
    }

    public boolean canUseFile(final Long fileId) {
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
