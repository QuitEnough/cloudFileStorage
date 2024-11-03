package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.Optional;

public interface FileService {

    boolean save(String name, MultipartFile file, Long directoryId);

    void delete(Long fileId);

    MultipartFile download(Long fileId);

}
