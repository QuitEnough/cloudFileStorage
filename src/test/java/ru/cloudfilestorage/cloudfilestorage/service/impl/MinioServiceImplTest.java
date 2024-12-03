package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class MinioServiceImplTest {

    private final String bucket = mock(String.class);

    private final MinioClient minioClient = mock(MinioClient.class);

    private final MinioServiceImpl minioService = new MinioServiceImpl(bucket, minioClient);

    @Test
    void fileSavedSuccess() {

    }

}