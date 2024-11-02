package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RestoreObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.exception.BaseException;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    @Value("${spring.minio.bucket}")
    private String bucket;

    private final FileRepository fileRepository;

    private MinioClient minioClient;

    @Override
    public boolean save(File file) {
        InputStream inputStream = new ByteArrayInputStream(file.getBytes()); // как совместить два класса io.Files и мой
        try {
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .stream(inputStream, inputStream.available(), -1)
                            .bucket(bucket)
                            .object(file.getUuid().toString())
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
    public File find(UUID uuid) {
        return null;
    }

    /*
    * minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build());
    * */

    /*InputStream in = new ByteArrayInputStream(file.getBytes());
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
            .build()*/

}
