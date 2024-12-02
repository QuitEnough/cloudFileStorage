package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
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
    void whenSaveFileThenReturnUserIdSuccess() {
        UUID uuid = UUID.randomUUID();
        try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
            File file = File
                    .builder()
                    .id(1L)
                    .name("name")
                    .uuid(uuid)
                    .directoryId(1L)
                    .userId(1L)
                    .build();

            mockedUuid.when(UUID::randomUUID).thenReturn(uuid);

            fileService.save(file.getName(), file.getDirectoryId(), file.getUserId());
            verify(fileRepository).save(any());
        }
    }

    @Test
    void fileDeleteSuccess() {
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(UUID.randomUUID())
                .directoryId(1L)
                .userId(1L)
                .build();

        fileService.delete(file.getId());
        verify(fileRepository).deleteById(any());
    }

    @Test
    void fileFoundSuccess() {
        UUID uuid = UUID.randomUUID();
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(uuid)
                .directoryId(1L)
                .userId(1L)
                .build();

        fileService.find(file.getId());
        verify(fileRepository).findById(any());
    }

    @Test
    void fileDownloadedSuccess() {
        UUID uuid = UUID.randomUUID();
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(uuid)
                .directoryId(1L)
                .userId(1L)
                .build();

        fileService.find(file.getId());
        verify(minioService).find(any());
    }

    @Test
    void findAllFilesByUserIdSuccess() {
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(UUID.randomUUID())
                .directoryId(1L)
                .userId(1L)
                .build();

        fileService.findAllFilesByUserId(file.getUserId());
        verify(fileRepository).findFilesByUserId(any());
    }

    @Test
    void findAllFilesInCertainDirSuccess() {
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(UUID.randomUUID())
                .directoryId(1L)
                .userId(1L)
                .build();

        fileService.findAllFilesInCertainDir(file.getDirectoryId());
        verify(fileRepository).findFilesByDirectoryId(any());
    }

}