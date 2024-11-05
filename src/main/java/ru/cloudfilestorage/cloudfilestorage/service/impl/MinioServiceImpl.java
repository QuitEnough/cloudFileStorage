package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.exception.BaseException;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
//@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

//    @Value("${spring.minio.bucket}")
    private final String bucket;

    private final FileRepository fileRepository;

    private final MinioClient minioClient;

    public MinioServiceImpl(@Value("files-bucket") String bucket, FileRepository fileRepository, MinioClient minioClient) {
        this.bucket = bucket;
        this.fileRepository = fileRepository;
        this.minioClient = minioClient;
    }

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
            throw new BaseException("Unable to save file", e);
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
