package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = """
            SELECT f FROM File f
            WHERE f.userId = :userId
            """)
    List<File> findAllByUserId(@Param("userId") Long userId);

}
