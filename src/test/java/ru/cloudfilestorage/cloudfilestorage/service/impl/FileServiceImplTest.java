package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.repository.FileRepository;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FileServiceImplTest {

    private final FileRepository fileRepository = mock(FileRepository.class);

    private final MinioServiceImpl minioService = mock(MinioServiceImpl.class);

    private final FileServiceImpl fileService = new FileServiceImpl(fileRepository, minioService);

    @Test
    void whenSaveFileThenReturnUserId() {
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
    void fileDelete() {
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
    void fileFound() {
        UUID uuid = UUID.randomUUID();
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(uuid)
                .directoryId(1L)
                .userId(1L)
                .build();

        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));

        UUID response = fileService.find(file.getId());

        assertNotNull(response);
        assertEquals(file.getUuid(), response);
    }

    @Test
    void fileDownloaded() {
        UUID uuid = UUID.randomUUID();
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(uuid)
                .directoryId(1L)
                .userId(1L)
                .build();

        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));
        when(minioService.find(uuid)).thenReturn(InputStream.nullInputStream());

        InputStream response = fileService.download(file.getId());

        assertNotNull(response);
    }

    @Test
    void findAllFilesByUserId() {
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(UUID.randomUUID())
                .directoryId(1L)
                .userId(1L)
                .build();

        when(fileRepository.findFilesByUserId(file.getUserId())).thenReturn(List.of(file));

        List<File> files = fileService.findAllFilesByUserId(file.getUserId());

        assertNotNull(files);
        assertEquals(file.getId(), files.get(0).getId());
        assertEquals(file.getName(), files.get(0).getName());
        assertEquals(file.getUuid(), files.get(0).getUuid());
        assertEquals(file.getDirectoryId(), files.get(0).getDirectoryId());
        assertEquals(file.getUserId(), files.get(0).getUserId());
    }

    @Test
    void findAllFilesInCertainDir() {
        File file = File
                .builder()
                .id(1L)
                .name("name")
                .uuid(UUID.randomUUID())
                .directoryId(1L)
                .userId(1L)
                .build();

        when(fileRepository.findFilesByDirectoryId(file.getDirectoryId())).thenReturn(List.of(file));

        List<File> files = fileService.findAllFilesInCertainDir(file.getDirectoryId());

        assertNotNull(files);
        assertEquals(file.getId(), files.get(0).getId());
        assertEquals(file.getName(), files.get(0).getName());
        assertEquals(file.getUuid(), files.get(0).getUuid());
        assertEquals(file.getDirectoryId(), files.get(0).getDirectoryId());
        assertEquals(file.getUserId(), files.get(0).getUserId());
    }

}