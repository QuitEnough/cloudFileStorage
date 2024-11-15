package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;

import java.util.List;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    List<Directory> findDirectoriesByUserId(Long userId);

    List<Directory> findDirectoriesByParentId(Long directoryId);

}
