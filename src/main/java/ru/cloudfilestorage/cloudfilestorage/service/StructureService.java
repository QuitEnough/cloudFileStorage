package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.service.impl.DirectoryServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StructureService {

    private final FileServiceImpl fileService;

    private final DirectoryServiceImpl directoryService;

    @Autowired
    public StructureService(FileServiceImpl fileService, DirectoryServiceImpl directoryService) {
        this.fileService = fileService;
        this.directoryService = directoryService;
    }

    public Map<Long, List<File>> doStructureFiles(List<File> fileList, Set<Directory> directorySet) {
        Map<Long, List<File>> structuredMap = new HashMap<>();

        structuredMap.put(null,
                fileList.stream().filter(file -> file.getDirectoryId() == null).toList());

        List<Long> directoryIdList = directorySet.stream().map(Directory::getId).toList();
        for (Long directoryId : directoryIdList) {
            structuredMap.put(directoryId,
                    fileList.stream().filter(file -> file.getDirectoryId().equals(directoryId)).toList());
        }

        return structuredMap;
    }

}
