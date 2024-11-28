package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.junit.jupiter.api.Test;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceImplTest {

    private final FileRepository fileRepository = mock(FileRepository.class);

    private final MinioServiceImpl minioService = mock(MinioServiceImpl.class);

    private final FileServiceImpl fileService = new FileServiceImpl(fileRepository, minioService);

    @Test
    void whenSaveFileThenReturnUserId_success() {
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(any())
                .directoryId(1L)
                .userId(1L)
                .build();

        var fileId = fileService.save(file.getName(), file.getDirectoryId(), file.getUserId());
        verify(fileRepository).save(file);
        assertEquals(1L, fileId);
    }

}