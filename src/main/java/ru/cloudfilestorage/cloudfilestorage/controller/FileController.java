package ru.cloudfilestorage.cloudfilestorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;

@RestController
public class FileController {

    private FileServiceImpl fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Void> handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file) {
        fileService.save(name, file);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
