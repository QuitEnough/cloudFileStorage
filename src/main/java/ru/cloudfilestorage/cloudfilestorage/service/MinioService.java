package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface MinioService {

    boolean save(UUID uuid, MultipartFile multipartFile); //Multipart можно ли положить в БД

    void delete(UUID uuid);

    InputStream find(UUID uuid);

}
