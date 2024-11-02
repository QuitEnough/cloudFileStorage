package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileDto {

    @NotNull(message = "File must be not null")
    private MultipartFile file;

}
