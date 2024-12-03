package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MinioServiceImplTest {

    private final String bucket = "files-bucket";

    private final MinioClient minioClient = mock(MinioClient.class);

    private final MinioServiceImpl minioService = new MinioServiceImpl(bucket, minioClient);

    @Test
    void fileSavedSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test".getBytes());
        minioService.save(uuid, mockMultipartFile);
        verify(minioClient).putObject(any());

    }

    @Test
    void fileDeletedSuccess() throws Exception {
        minioService.delete(UUID.randomUUID());
        verify(minioClient).removeObject(any());
    }

    @Test
    void fileFoundSuccess() throws Exception {
        minioService.find(UUID.randomUUID());
        verify(minioClient).getObject(any());
    }

}