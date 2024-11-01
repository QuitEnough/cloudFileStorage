package ru.cloudfilestorage.cloudfilestorage.domain.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MinioObject {

    private String name;

    private String path;

    private boolean isDirectory;

}
