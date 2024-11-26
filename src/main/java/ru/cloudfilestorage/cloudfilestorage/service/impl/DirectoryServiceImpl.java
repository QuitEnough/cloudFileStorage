package ru.cloudfilestorage.cloudfilestorage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.repository.DirectoryRepository;
import ru.cloudfilestorage.cloudfilestorage.service.DirectoryService;

import java.util.List;

@Service
@Slf4j
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;

    @Autowired
    public DirectoryServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Override
    public List<Directory> findDirectoriesByUserId(Long userId) {
        log.debug("[DirectoryService] finding directories for user with id {}", userId);
        return directoryRepository.findDirectoriesByUserId(userId);
    }

    public List<Directory> findAllDirectoriesInCertainDir(Long directoryId) {
        log.debug("[DirectoryService] finding directories in directory with id {}", directoryId);
        return directoryRepository.findDirectoriesByParentId(directoryId);
    }

}
