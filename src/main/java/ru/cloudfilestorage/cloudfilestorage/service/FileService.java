package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface FileService {

    boolean save(String name, MultipartFile file, Long directoryId);

    void delete(Long fileId);

    UUID find(Long fileId);

    InputStream download(Long fileId);

}