package ru.cloudfilestorage.cloudfilestorage.domain.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {

    private final static String ROLE_PREFIX = "ROLE_";

    private Long id;

    private String password;

    private String email;

    private Role role;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE_PREFIX + role.name()));
    }
}

