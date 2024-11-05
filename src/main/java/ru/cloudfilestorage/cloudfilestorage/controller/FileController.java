package ru.cloudfilestorage.cloudfilestorage.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.exception.BaseException;
import ru.cloudfilestorage.cloudfilestorage.service.MinioService;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;

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
@AllArgsConstructor
public class FileController {

    private FileServiceImpl fileService;

    private MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("name") String name,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam("directory_id") Long directoryId) {
        fileService.save(name, file, directoryId);
        minioService.save(UUID.fromString(name), file);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/find")
    public void findFile(@RequestParam("fileId") Long fileId, HttpServletResponse response) {
        try (InputStream stream = fileService.download(fileId)) {
            response.setHeader("Content-Disposition", "attachment");
            response.setStatus(HttpServletResponse.SC_OK);
            FileCopyUtils.copy(stream, response.getOutputStream());
        } catch (IOException e) {
            throw new BaseException("Unable to download file");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam("id") Long fileId) {
        fileService.delete(fileId);
        minioService.delete(fileService.find(fileId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
