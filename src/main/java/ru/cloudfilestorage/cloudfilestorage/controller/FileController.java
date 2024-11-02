package ru.cloudfilestorage.cloudfilestorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * если не будет работать метод контроллера findFile --> попробовать с потоком
 *          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
 *          byteArrayOutputStream.write(minioService.find(null).getBytes());
 */
@RestController
public class FileController {

    private FileServiceImpl fileService;

    private MinioService minioService;

    @PostMapping("upload")
    public ResponseEntity<Void> upload(@RequestParam("name") String name,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam("directory_id") Long directoryId) {
        fileService.save(name, file);
        minioService.save(UUID.fromString(name), file);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/find")
    public MultipartFile findFile(@RequestParam("fileId") Long fileId) throws IOException { //погуглить, нужно открывать поток

        fileService.download(fileId);
        return minioService.find(null);
    }

}
