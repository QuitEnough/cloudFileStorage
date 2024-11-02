package ru.cloudfilestorage.cloudfilestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, Long> {

    void delete(Long id);

    void findByUuid(UUID uuid);

}
