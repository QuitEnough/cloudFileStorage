package ru.cloudfilestorage.cloudfilestorage.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.List;
import java.util.UUID;

public interface FileRepository extends JpaRepository<File, Long> {

    void deleteById(@NotNull Long fileId);

    void findByUuid(UUID uuid);

    List<File> findFilesByUserId(Long userId);

    List<File> findFilesByDirectoryId(Long directoryId);

}
