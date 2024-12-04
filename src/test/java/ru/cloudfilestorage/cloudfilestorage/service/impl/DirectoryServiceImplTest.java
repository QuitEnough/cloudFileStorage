package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.junit.jupiter.api.Test;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.repository.DirectoryRepository;

import java.util.List;

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
    }

}