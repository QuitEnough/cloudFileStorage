package ru.cloudfilestorage.cloudfilestorage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public interface MinioService {

    boolean save(UUID uuid, MultipartFile multipartFile); //Multipart можно ли положить в БД

    void delete(UUID uuid);

    InputStream find(UUID uuid);

}
