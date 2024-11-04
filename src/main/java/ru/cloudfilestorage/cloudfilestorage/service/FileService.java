package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public interface FileService {

    boolean save(String name, MultipartFile file, Long directoryId);

    void delete(Long fileId);

    UUID find(Long fileId);

    InputStream download(Long fileId);

}
