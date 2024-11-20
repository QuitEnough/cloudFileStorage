package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeFile {

    private String type;

    private Long id;

    private String name;

    @JsonIgnore
    private Long parentId;
}
