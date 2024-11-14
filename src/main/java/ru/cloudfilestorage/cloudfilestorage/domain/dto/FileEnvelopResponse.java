package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FileEnvelopResponse {

    private String directoryName;

    private List<String> filesName;

    public static FileEnvelopResponse generateFileEnvelopResponse(String directoryName, List<String> filesName) {
        return FileEnvelopResponse
                .builder()
                .directoryName(directoryName)
                .filesName(filesName)
                .build();
    }

}
