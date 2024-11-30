package ru.cloudfilestorage.cloudfilestorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Role;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.UserDetailsImpl;
import ru.cloudfilestorage.cloudfilestorage.service.JwtService;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.MinioServiceImpl;

import java.io.InputStream;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileServiceImpl fileService;

    @MockBean
    private MinioServiceImpl minioService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenUploadFileThenReturnResponse200() throws Exception {
        File file = File
                .builder()
                .id(1L)
                .name("file")
                .uuid(UUID.randomUUID())
                .directoryId(1L)
                .userId(1L)
                .build();

        when(fileService.save(
                file.getName(),
                file.getDirectoryId(),
                file.getUserId()))
                .thenReturn(file.getId());

        UserDetailsImpl user = UserDetailsImpl
                .builder()
                .id(1L)
                .password("BilanBilan")
                .email("DimaBilan@mail.ru")
                .role(Role.USER)
                .build();

        when(jwtService.extractEmail(any())).thenReturn("test");
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);

        MockMultipartFile multiFile = new MockMultipartFile("file", "test".getBytes());
        this.mvc
                .perform(multipart("/files/upload")
                        .file(multiFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFbWFpbFdpdGhTdHJvbmdQYXNzd29yZEBtYWlsLnJ1IiwiaWF0IjoxNzMyOTUyMzI1LCJleHAiOjE3MzMwOTYzMjV9.JGVw4jT3QmMbUYXWF4bHGd4Jsys3W-U9wb5Ij83Did9dRvFVY2GCoYCjruSSxDyLy1_Ku-EKtlBEYZIIBmbQZA")
                        .param("name", "Bob")
                        .param("directory_id", "1L"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
