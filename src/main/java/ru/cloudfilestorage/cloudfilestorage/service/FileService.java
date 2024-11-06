package ru.cloudfilestorage.cloudfilestorage.service;

import java.io.InputStream;
import java.util.UUID;

public interface FileService {

    Long save(String name, Long directoryId);

    void delete(Long fileId);

    UUID find(Long fileId);

    InputStream download(Long fileId);

}
