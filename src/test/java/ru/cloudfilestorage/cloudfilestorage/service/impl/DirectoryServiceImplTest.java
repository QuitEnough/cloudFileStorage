package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.junit.jupiter.api.Test;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.repository.DirectoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DirectoryServiceImplTest {

    private final DirectoryRepository directoryRepository = mock(DirectoryRepository.class);

    private final DirectoryServiceImpl directoryService = new DirectoryServiceImpl(directoryRepository);

    @Test
    void directoriesFoundByUserId() {
        Directory directory = Directory
                .builder()
                .id(1L)
                .name("dir")
                .userId(1L)
                .parentId(1L)
                .build();

        when(directoryRepository.findDirectoriesByUserId(directory.getUserId())).thenReturn(List.of(directory));

        List<Directory> directories = directoryService.findDirectoriesByUserId(1L);

        assertNotNull(directories);
        assertEquals(1L, directories.get(0).getId());
        assertEquals("dir", directories.get(0).getName());
        assertEquals(1L, directories.get(0).getUserId());
        assertEquals(1L, directories.get(0).getParentId());
    }

    @Test
    void allDirectoriesFoundInCertainDir() {
        Directory directory = Directory
                .builder()
                .id(1L)
                .name("dir")
                .userId(1L)
                .parentId(1L)
                .build();

        when(directoryRepository.findDirectoriesByParentId(directory.getId())).thenReturn(List.of(directory));

        List<Directory> directories = directoryService.findAllDirectoriesInCertainDir(1L);

        assertNotNull(directories);
        assertEquals(1L, directories.get(0).getId());
        assertEquals("dir", directories.get(0).getName());
        assertEquals(1L, directories.get(0).getUserId());
        assertEquals(1L, directories.get(0).getParentId());
    }

}