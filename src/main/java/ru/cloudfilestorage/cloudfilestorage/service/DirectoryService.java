package ru.cloudfilestorage.cloudfilestorage.service;

import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;

import java.util.List;

public interface DirectoryService {

    List<Directory> findDirectoriesByUserId(Long userId);

}
