package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;
import ru.cloudfilestorage.cloudfilestorage.service.FileService;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final MinioServiceImpl minioService;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, MinioServiceImpl minioService) {
        this.fileRepository = fileRepository;
        this.minioService = minioService;
    }

    @Override
    public Long save(String name, Long directoryId) {
        UUID uuid = UUID.randomUUID();

        File file = File
                .builder()
                .name(name)
                .uuid(uuid)
//                .directoryId(directoryId)
                .build();

        fileRepository.save(file);
        return file.getId();
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
