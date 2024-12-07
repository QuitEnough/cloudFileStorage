package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.exception.FileActionException;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    private final String bucket;

    private final MinioClient minioClient;

    @Autowired
    public MinioServiceImpl(@Value("${spring.minio.bucket}") String bucket, MinioClient minioClient) {
        this.bucket = bucket;
        this.minioClient = minioClient;
    }

    @Override
    public boolean save(UUID uuid, MultipartFile multipartFile) {

        log.debug("[MinioService] Saving file {} with uuid {}", multipartFile, uuid);

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
            throw new FileActionException("Unable to save file", e);
        }
        return true;
    }

    @Override
    public void delete(UUID uuid) {

        log.debug("[MinioService] deleting file with uuid {}", uuid);

        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(uuid.toString())
                            .build());
        } catch (Exception e) {
            throw new FileActionException("Unable to delete file", e);
        }
    }

    @Override
    public InputStream find(UUID uuid) {

        log.debug("[MinioService] finding file with uuid {}", uuid);

        try {
            return minioClient.getObject(
                    GetObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(uuid.toString())
                            .build());
        } catch (Exception e) {
            throw new FileActionException("Unable to download", e);
        }
    }

}
