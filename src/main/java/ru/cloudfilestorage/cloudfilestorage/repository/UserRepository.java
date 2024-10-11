package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.cloudfilestorage.cloudfilestorage.entity.User;
import ru.cloudfilestorage.cloudfilestorage.model.UserResponse;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
