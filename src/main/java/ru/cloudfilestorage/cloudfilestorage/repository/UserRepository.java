package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}
