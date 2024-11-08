package ru.cloudfilestorage.cloudfilestorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.UserDetailsImpl;
import ru.cloudfilestorage.cloudfilestorage.exception.FileActionException;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.MinioServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@Tag(name = "File Controller", description = "File API")
public class FileController {

    private final FileServiceImpl fileService;

    private final MinioServiceImpl minioService;

    @Autowired
    public FileController(FileServiceImpl fileService, MinioServiceImpl minioService) {
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload the File")
    public ResponseEntity<Void> uploadFile(@RequestParam("name") String name,
                                           @RequestParam("file") @NotNull(message = "File must be not null") MultipartFile file,
                                           @RequestParam(name = "directory_id", required = false) Long directoryId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetailsImpl) authentication.getPrincipal();

        long fileId = fileService.save(name, directoryId, user.getId());
        UUID uuid = fileService.find(fileId);
        minioService.save(uuid, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find")
    @PreAuthorize("@cse.canAccessFile(#fileId)")
    @Operation(summary = "Download the File")
    public void findFile(@RequestParam("fileId") Long fileId, HttpServletResponse response) {
        try (InputStream stream = fileService.download(fileId)) {
            response.setHeader("Content-Disposition", "attachment");
            response.setStatus(HttpServletResponse.SC_OK);
            FileCopyUtils.copy(stream, response.getOutputStream());
        } catch (IOException e) {
            throw new FileActionException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@cse.canAccessFile(#fileId)")
    @Operation(summary = "Delete the File")
    public ResponseEntity<Void> deleteFile(@RequestParam("id") Long fileId) {
        minioService.delete(fileService.find(fileId));
        fileService.delete(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
