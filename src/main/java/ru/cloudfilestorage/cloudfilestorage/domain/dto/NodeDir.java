package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NodeDir {

    private String type;

    private Long id;

    private String name;

    @JsonIgnore
    private Long parentId;

    @JsonIgnore
    private List<NodeDir> childrenDirs;

    @JsonIgnore
    private List<NodeFile> files;
}
