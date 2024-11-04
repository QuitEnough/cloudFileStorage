package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
}
