package ru.cloudfilestorage.cloudfilestorage.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.service.impl.DirectoryServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;

class StructureServiceTest {

    private final FileServiceImpl fileService = Mockito.mock(FileServiceImpl.class);
    private final DirectoryServiceImpl directoryService = Mockito.mock(DirectoryServiceImpl.class);
    private final StructureService service = new StructureService(fileService, directoryService);

    @Test
    void getEnvelopeDirsForUser() {
        Long userId = 1L;

        List<Directory> dirs = List.of(
                new Directory() {{
                    setId(1L);
                    setName("dir");
                    setUserId(userId);
                    setParentId(null);
                }},
                new Directory() {{
                    setId(2L);
                    setName("root");
                    setUserId(userId);
                    setParentId(null);
                }},
                new Directory() {{
                    setId(3L);
                    setName("test");
                    setUserId(userId);
                    setParentId(1L);
                }},
                new Directory() {{
                    setId(4L);
                    setName("root 5");
                    setUserId(userId);
                    setParentId(2L);
                }}
        );

        List<File> files = List.of(
                new File() {{
                    setId(1L);
                    setName("root");
                    setUserId(userId);
                    setDirectoryId(null);
                }},
                new File() {{
                    setId(2L);
                    setName("dir");
                    setUserId(userId);
                    setDirectoryId(1L);
                }},
                new File() {{
                    setId(3L);
                    setName("test");
                    setUserId(userId);
                    setDirectoryId(4L);
                }}
        );

        when(directoryService.findDirectoriesByUserId(userId)).thenReturn(dirs);
        when(fileService.findAllFilesByUserId(userId)).thenReturn(files);

        service.getEnvelopeDirsForUser(userId);
    }
}