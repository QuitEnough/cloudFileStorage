package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.cloudfilestorage.cloudfilestorage.exception.BaseException;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    @Value("${spring.minio.bucket}")
    private String bucket;

    private final FileRepository fileRepository;

    private MinioClient minioClient;

    @Override
    public boolean save(UUID uuid, MultipartFile multipartFile) {
        try {
            InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .stream(inputStream, inputStream.available(), -1)
                            .bucket(bucket)
                            .object(uuid.toString())
                            .build());
        } catch (Exception e) {
            throw new BaseException("Unable to delete file");
        }
        return true;
    }

    @Override
    public void delete(UUID uuid) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(uuid.toString())
                            .build());
        } catch (Exception e) {
            throw new BaseException("Unable to delete file");
        }
    }

    @Override
    public InputStream find(UUID uuid) {
        try {
            return minioClient.getObject(
                    GetObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(uuid.toString())
                            .build());
        } catch (Exception e) {
            throw new BaseException("Unable to download");
        }
    }

}
