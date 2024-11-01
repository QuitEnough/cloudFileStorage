package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.MinioObject;

import java.util.List;

public interface MinioService {

    List<MinioObject> folderList(String userFolder);

    File getFileById(Long id);

    List<File> getAllFilesByUserId(Long id);

    void createFolder(String folderName);

    boolean folderExists(String folderName);

    void deleteFolder(String[] folderName);

    void uploadFile(String userDirectory, MultipartFile[] files);

    void renameFile(String filePath, String fileNewName);

    void renameDirectory(String filePath, String fileName);

}
