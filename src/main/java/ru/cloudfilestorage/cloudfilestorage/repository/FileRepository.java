package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = """
            SELECT * FROM files f
            JOIN users_files uf ON uf.files_id = f.id
            WHERE uf.user_id = :userId
            """, nativeQuery = true)
    List<File> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
            INSERT INTO users_files(user_id, files_id)
            VALUES (:userId, :files_id)
            """, nativeQuery = true)
    void assignFile(@Param("userId") Long userId, @Param("files_id") Long fileId);

}
