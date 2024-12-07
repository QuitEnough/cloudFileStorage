package ru.cloudfilestorage.cloudfilestorage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;
import ru.cloudfilestorage.cloudfilestorage.service.FileService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final MinioServiceImpl minioService;

    public FileServiceImpl(FileRepository fileRepository,
                           MinioServiceImpl minioService) {

        this.fileRepository = fileRepository;
        this.minioService = minioService;
    }

    @Override
    public Long save(String name, Long directoryId, Long userId) {

        log.debug("[FileService] saving file with name {}, directoryId {}, userId {}", name, directoryId, userId);

        UUID uuid = UUID.randomUUID();

        File file = File
                .builder()
                .name(name)
                .uuid(uuid)
                .directoryId(directoryId)
                .userId(userId)
                .build();

        fileRepository.save(file);
        return file.getId();
    }

    @Override
    public void delete(Long fileId) {
        log.debug("[FileService] deleting file with id {}", fileId);
        fileRepository.deleteById(fileId);
    }

    @Override
    public UUID find(Long fileId) {

        log.debug("[FileService] finding file with id {}", fileId);

        File file = fileRepository.findById(fileId).get();
        return file.getUuid();
    }

    @Override
    public InputStream download(Long fileId) {

        log.debug("[FileService] downloading file with id {}", fileId);

        File file = fileRepository.findById(fileId).get();
        return minioService.find(file.getUuid());
    }

    @Override
    public List<File> findAllFilesByUserId(Long userId) {
        log.debug("[FileService] finding files for user with id {}", userId);
        return fileRepository.findFilesByUserId(userId);
    }

    public List<File> findAllFilesInCertainDir(Long directoryId) {
        log.debug("[FileService] finding files for user in directory with id {}", directoryId);
        return fileRepository.findFilesByDirectoryId(directoryId);
    }

}
