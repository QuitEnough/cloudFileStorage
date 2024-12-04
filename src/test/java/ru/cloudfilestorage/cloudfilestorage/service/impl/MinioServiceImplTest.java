package ru.cloudfilestorage.cloudfilestorage.service.impl;

import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MinioServiceImplTest {

    private final String bucket = "files-bucket";

    private final MinioClient minioClient = mock(MinioClient.class);

    private final MinioServiceImpl minioService = new MinioServiceImpl(bucket, minioClient);

    @Test
    void fileSavedSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test".getBytes());

        boolean response = minioService.save(uuid, mockMultipartFile);
        verify(minioClient).putObject(any());

        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void fileDeleted() throws Exception {
        minioService.delete(UUID.randomUUID());
        verify(minioClient).removeObject(any());
    }

    @Test
    void fileFound() throws Exception {
        GetObjectResponse result = minioClient.getObject(any());

        when(minioClient.getObject(any())).thenReturn(result);
        InputStream response = minioService.find(UUID.randomUUID());

        assertEquals(result, response);
    }

}