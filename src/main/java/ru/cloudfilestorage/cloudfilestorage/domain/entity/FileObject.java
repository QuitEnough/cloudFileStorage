package ru.cloudfilestorage.cloudfilestorage.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileObject {

    private MultipartFile file;

}
