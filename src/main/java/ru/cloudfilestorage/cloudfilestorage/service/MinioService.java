package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public interface MinioService {

    boolean save(UUID uuid, MultipartFile multipartFile); //Multipart можно ли положить в БД

    void delete(UUID uuid);

    MultipartFile find(UUID uuid);

}
