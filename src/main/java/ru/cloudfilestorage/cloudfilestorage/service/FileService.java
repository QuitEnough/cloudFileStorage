package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;

import java.util.Optional;

public interface FileService {

    boolean save(String name, MultipartFile file);

    void delete(Long fileId);

    Optional<File> find(Long fileId);

}
