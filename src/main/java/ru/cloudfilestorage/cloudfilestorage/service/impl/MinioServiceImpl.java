package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.MinioObject;
import ru.cloudfilestorage.cloudfilestorage.domain.mapper.MinioMapper;
import ru.cloudfilestorage.cloudfilestorage.exception.ServiceCustomException;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    @Value("${spring.minio.bucket}")
    private String bucket;

    private final FileRepository fileRepository;

    private MinioClient minioClient;

    @Override
    public List<MinioObject> folderList(String userFolder) {
        Iterable<Result<Item>> results = minioClient
                .listObjects(
                        ListObjectsArgs.builder()
                            .bucket(bucket)
                            .prefix(userFolder)
                            .recursive(false)
                            .build());
        return MinioMapper.convert(results);
    }

    @Override
    public File getFileById(Long id) {
        return fileRepository
                .findById(id)
                .orElseThrow(() -> new ServiceCustomException("File not found"));
    }

    @Override
    public List<File> getAllFilesByUserId(Long id) {
        return fileRepository.findAllByUserId(id);
    }

    @Override
    public void createFolder(String folderName) {
        if (folderExists(folderName)) {
            return;
        }

        String objectName = StringUtils.join(folderName, "/");

        try {
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build());
        } catch (Exception e) {
            throw new ServiceCustomException("File creation failed");
        }
    }

    @Override
    public boolean folderExists(String folderName) {
        return folderList(folderName).iterator().hasNext();
    }

    @Override
    public void deleteFolder(String[] folderName) {

    }

    @Override
    public void uploadFile(String userDirectory, MultipartFile[] files) {

        createBucket();

        try {
            for (MultipartFile file : files) {
                InputStream in = new ByteArrayInputStream(file.getBytes());
                String fileName = file.getOriginalFilename();
                String objectName = StringUtils.join(userDirectory, "/", fileName);

                minioClient.putObject(
                        PutObjectArgs
                                .builder()
                                .bucket(bucket)
                                .object(objectName)
                                .stream(
                                        in, file.getSize(), -1
                                )
                                .contentType(file.getContentType())
                                .build()
                );
            }
        } catch (Exception e) {
            throw new ServiceCustomException("Failed upload file");
        }

    }

    @Override
    public void renameFile(String filePath, String fileNewName) {

    }

    @Override
    public void renameDirectory(String filePath, String fileName) {

    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient
                .bucketExists(BucketExistsArgs
                        .builder()
                        .bucket(bucket)
                        .build());
        if (!found) {
            minioClient
                    .makeBucket(MakeBucketArgs
                            .builder()
                            .bucket(bucket)
                            .build());
        }
    }

}
