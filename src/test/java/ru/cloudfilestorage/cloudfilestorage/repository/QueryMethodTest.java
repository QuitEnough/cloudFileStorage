package ru.cloudfilestorage.cloudfilestorage.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cloudfilestorage.cloudfilestorage.entity.User;

@SpringBootTest
public class QueryMethodTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserNameJPQLIndexParam() {
        User user = userRepository.findByUserNameJPQLIndexParam("usrNameCheckIndexParam - JPQL");
        System.out.println(user);
    }

    @Test
    void findByUserNameJPQLNamedParam() {
        User user = userRepository.findByUserNameJPQLNamedParam("usrNameCheckNamedParam - JPQL");
        System.out.println(user);
    }

    @Test
    void findByUserNameSQLIndexParam() {
        User user = userRepository.findByUserNameSQLIndexParam("usrNameCheckIndexParam - SQL");
        System.out.println(user);
    }

    @Test
    void findByUserNameSQLNamedParam() {
        User user = userRepository.findByUserNameSQLNamedParam("usrNameCheckNamedParam - SQL");
        System.out.println(user);
    }

    @Test
    void findByUserNameAndId() {
        User user = userRepository.findByUserNameAndId("usrNameCheckAnnotation", 1L);
        System.out.println(user);
    }

}
