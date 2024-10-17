package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cloudfilestorage.cloudfilestorage.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
