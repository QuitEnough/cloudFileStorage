package ru.cloudfilestorage.cloudfilestorage.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    void deleteById(@NotNull Long fileId);

    void findByUuid(UUID uuid);

}
