package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NodeDir {

    private String type;

    private Long id;

    private String name;

    private Long parentId;

    private List<NodeDir> childrenDirs;

    private List<NodeFile> files;
}
