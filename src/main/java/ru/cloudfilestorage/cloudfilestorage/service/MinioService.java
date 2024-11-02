package ru.cloudfilestorage.cloudfilestorage.service;


import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.UUID;

public interface MinioService {

    boolean save(File file);

    void delete(UUID uuid);

    File find(UUID uuid);

}
