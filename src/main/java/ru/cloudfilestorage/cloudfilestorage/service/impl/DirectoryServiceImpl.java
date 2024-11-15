package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.repository.DirectoryRepository;
import ru.cloudfilestorage.cloudfilestorage.service.DirectoryService;

import java.util.List;
import java.util.Set;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;

    private final FileServiceImpl fileService;

    @Autowired
    public DirectoryServiceImpl(DirectoryRepository directoryRepository, FileServiceImpl fileService) {
        this.directoryRepository = directoryRepository;
        this.fileService = fileService;
    }

    @Override
    public Set<Directory> findDirectoriesByUserId(Long userId) {
        return directoryRepository.findDirectoriesByUserId(userId);
    }

    public List<Directory> findAllDirectoriesInCertainDir(Long directoryId) {
        return directoryRepository.findDirectoriesByParentId(directoryId);
    }

}
