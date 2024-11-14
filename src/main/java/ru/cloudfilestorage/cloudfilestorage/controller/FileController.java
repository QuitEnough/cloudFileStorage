package ru.cloudfilestorage.cloudfilestorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.FileEnvelopResponse;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.UserDetailsImpl;
import ru.cloudfilestorage.cloudfilestorage.exception.FileActionException;
import ru.cloudfilestorage.cloudfilestorage.service.StructureService;
import ru.cloudfilestorage.cloudfilestorage.service.impl.DirectoryServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.MinioServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@Tag(name = "File Controller", description = "File API")
public class FileController {

    private final FileServiceImpl fileService;

    private final DirectoryServiceImpl directoryService;

    private final MinioServiceImpl minioService;

    private final StructureService structureService;

    @Autowired
    public FileController(FileServiceImpl fileService,
                          DirectoryServiceImpl directoryService,
                          MinioServiceImpl minioService,
                          StructureService structureService) {

        this.fileService = fileService;
        this.directoryService = directoryService;
        this.minioService = minioService;
        this.structureService = structureService;
    }

    @GetMapping("/getFiles/{id}")
    public FileEnvelopResponse getUserFilesView(@PathVariable("id") Long userId) {

        Map<Long, List<File>> structure = structureService.doStructureFiles(
                fileService.findAllFilesByUserId(userId),
                directoryService.findDirectoriesByUserId(userId));

        FileEnvelopResponse.generateFileEnvelopResponse(); // еще думаю
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload the File")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File saved"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Can not insert into table")
    })
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
    @PreAuthorize("@UserAccessor.canUseFile(#fileId)")
    @Operation(summary = "Download the File")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File achieved"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "File not found")
    })
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
    @PreAuthorize("@UserAccessor.canUseFile(#fileId)")
    @Operation(summary = "Delete the File")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File deleted"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "File not found")
    })
    public ResponseEntity<Void> deleteFile(@RequestParam("id") Long fileId) {
        minioService.delete(fileService.find(fileId));
        fileService.delete(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
