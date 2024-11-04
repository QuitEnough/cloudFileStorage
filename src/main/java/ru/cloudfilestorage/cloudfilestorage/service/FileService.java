package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface FileService {

    boolean save(String name, MultipartFile file, Long directoryId);

    void delete(Long fileId);

    UUID find(Long fileId);

    InputStream download(Long fileId);

}
