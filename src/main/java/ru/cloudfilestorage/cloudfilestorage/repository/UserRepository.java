package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = """
            SELECT f 
            FROM File f 
            WHERE f.id = :fileId 
            AND f.userId = :userId
            """)
    boolean isFileOwner(@Param("userId") Long userId, @Param("fileId") Long fileId);

}
