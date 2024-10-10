package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.cloudfilestorage.cloudfilestorage.entity.User;
import ru.cloudfilestorage.cloudfilestorage.model.UserResponse;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);


    // JPQL
    @Query("SELECT u from User u where u.userName = ?1")
    User findByUserNameJPQLIndexParam(String userName);

    @Query("SELECT u from User u where u.userName =:name")
    User findByUserNameJPQLNamedParam(@Param("name") String userName);

    // SQL
    @Query(value = "SELECT * from usr u where u.userName = ?1", nativeQuery = true)
    User findByUserNameSQLIndexParam(String userName);

    @Query(value = "SELECT * from usr u where u.userName =:name", nativeQuery = true)
    User findByUserNameSQLNamedParam(@Param("name") String userName);

    User findByUserNameAndId(String userName, long id);


}
