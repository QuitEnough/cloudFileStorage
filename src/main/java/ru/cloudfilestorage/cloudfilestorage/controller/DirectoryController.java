package ru.cloudfilestorage.cloudfilestorage.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.Node;
import ru.cloudfilestorage.cloudfilestorage.service.StructureService;

@RestController
@RequestMapping("/directories")
@Tag(name = "File Controller", description = "File API")
public class DirectoryController {

    private final StructureService structureService;

    @Autowired
    public DirectoryController(StructureService structureService) {
        this.structureService = structureService;
    }

    @GetMapping
    public Node getDataForDir(@RequestParam("id") Long dirId) {
        return structureService.getDataForCertainDir(dirId);
    }

    @GetMapping("/user")
    public Node getAllDataForUser(@RequestParam("id") Long userId) {
        return structureService.getEnvelopeDirsForUser(userId);
    }
}
