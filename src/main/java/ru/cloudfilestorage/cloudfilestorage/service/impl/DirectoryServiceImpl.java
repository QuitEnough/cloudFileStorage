package ru.cloudfilestorage.cloudfilestorage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.repository.DirectoryRepository;
import ru.cloudfilestorage.cloudfilestorage.service.DirectoryService;

import java.util.Set;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;

    @Autowired
    public DirectoryServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Override
    public Set<Directory> findDirectoriesByUserId(Long userId) {
        return directoryRepository.findDirectoriesByUserId(userId);
    }

}
