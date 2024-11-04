package ru.cloudfilestorage.cloudfilestorage.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.repository.DirectoryRepository;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;
import ru.cloudfilestorage.cloudfilestorage.service.FileService;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    private DirectoryRepository directoryRepository;

    private MinioServiceImpl minioService;

    @Override
    public boolean save(String name, MultipartFile multipartFile, Long directoryId) {
        String[] fileName = StringUtils.splitByWholeSeparator(name, "\\.");
        UUID uuid = UUID.fromString(name);

        File file = File
                .builder()
                .name(fileName[0])
                .extension(fileName[1])
                .uuid(uuid)
                .directoryId(directoryId)
                .build();

        fileRepository.save(file);

        return true;
    }

    @Override
    public void delete(Long fileId) {
        fileRepository.deleteById(fileId);
    }

    @Override
    public UUID find(Long fileId) {
        File file = fileRepository.findById(fileId).get();
        return file.getUuid();
    }

    @Override
    public InputStream download(Long fileId) {
        File file = fileRepository.findById(fileId).get();
        return minioService.find(file.getUuid());
    }

}
