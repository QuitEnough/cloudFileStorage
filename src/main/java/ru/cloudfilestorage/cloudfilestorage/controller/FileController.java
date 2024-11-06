package ru.cloudfilestorage.cloudfilestorage.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.exception.DownloadFileException;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.MinioServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * если не будет работать метод контроллера findFile --> попробовать с потоком
 *          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
 *          byteArrayOutputStream.write(minioService.find(null).getBytes());
 *          return minioService.find(null);
 */
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileServiceImpl fileService;

    private final MinioServiceImpl minioService;

    @Autowired
    public FileController(FileServiceImpl fileService, MinioServiceImpl minioService) {
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("name") String name,
                                       @RequestParam("file") @NotNull(message = "File must be not null") MultipartFile file,
                                       @RequestParam("directory_id") Long directoryId) {
        long fileId = fileService.save(name, directoryId);
        UUID uuid = fileService.find(fileId);
        minioService.save(uuid, file);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/find")
    public void findFile(@RequestParam("fileId") Long fileId, HttpServletResponse response) {
        try (InputStream stream = fileService.download(fileId)) {
            response.setHeader("Content-Disposition", "attachment");
            response.setStatus(HttpServletResponse.SC_OK);
            FileCopyUtils.copy(stream, response.getOutputStream());
        } catch (IOException e) {
            throw new DownloadFileException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam("id") Long fileId) {
        minioService.delete(fileService.find(fileId));
        fileService.delete(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
