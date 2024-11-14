package ru.cloudfilestorage.cloudfilestorage.service;

import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;

import java.util.Set;

public interface DirectoryService {

    Set<Directory> findDirectoriesByUserId(Long userId);

}
