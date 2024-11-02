package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

    void delete(Long id);

}
