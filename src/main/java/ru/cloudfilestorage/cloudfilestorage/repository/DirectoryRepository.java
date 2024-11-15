package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;

import java.util.List;
import java.util.Set;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    Set<Directory> findDirectoriesByUserId(Long userId);

    List<Directory> findDirectoriesByParentId(Long directoryId);

}
