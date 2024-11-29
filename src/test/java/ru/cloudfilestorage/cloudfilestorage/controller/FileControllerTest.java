package ru.cloudfilestorage.cloudfilestorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.MinioServiceImpl;

import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileServiceImpl fileService;

    @MockBean
    private MinioServiceImpl minioService;

    @Test
    @SneakyThrows
    void test() {
        mvc.perform(post("/files/upload")).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void whenUploadFileThenReturnResponse200() {

        MultipartFile file = new MockMultipartFile("Bob.jpg", InputStream.nullInputStream());

        mvc.perform(post("/files/upload")
                .contentType("application/json")
//                .header() //подложить хедер из постмана
                .param("name", "file")
//                .param("file", "Bob.jpg")
                .param("file", objectMapper.writeValueAsString(file))
//                .param("directory_id", "1L")
                .param("directory_id", objectMapper.writeValueAsString(1L)))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    @SneakyThrows
    void whenUploadFileThenReturnResponse500() {

        MultipartFile file = new MockMultipartFile("Bob.jpg", InputStream.nullInputStream());

        mvc.perform(post("/files/upload")
                        .contentType("application/json")
                        .param("name", "file")
//                .param("file", "Bob.jpg")
                        .param("file", objectMapper.writeValueAsString(null))
//                .param("directory_id", "1L")
                        .param("directory_id", objectMapper.writeValueAsString(1L)))
                .andExpect(status().is5xxServerError());


    }

}