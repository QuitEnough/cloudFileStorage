package ru.cloudfilestorage.cloudfilestorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.Node;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.NodeDir;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.NodeFile;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.Directory;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.File;
import ru.cloudfilestorage.cloudfilestorage.service.impl.DirectoryServiceImpl;
import ru.cloudfilestorage.cloudfilestorage.service.impl.FileServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StructureService {

    private final FileServiceImpl fileService;

    private final DirectoryServiceImpl directoryService;

    @Autowired
    public StructureService(FileServiceImpl fileService, DirectoryServiceImpl directoryService) {
        this.fileService = fileService;
        this.directoryService = directoryService;
    }

    public Node getDataForCertainDir(Long directoryId) {

        List<Directory> dirs = directoryService.findAllDirectoriesInCertainDir(directoryId);
        List<NodeDir> nodeDirsList = new ArrayList<>();

        for (Directory directory : dirs) {
            NodeDir nodeDir = NodeDir
                    .builder()
                    .type("dir")
                    .id(directory.getId())
                    .name(directory.getName())
                    .build();
            nodeDirsList.add(nodeDir);
        }

        List<File> files = fileService.findAllFilesInCertainDir(directoryId);
        List<NodeFile> nodeFileList = new ArrayList<>();

        List<File> certainFiles = files.stream().filter(file -> file.getDirectoryId().equals(directoryId)).toList();

        for (File file : certainFiles) {
            NodeFile nodeFile = NodeFile
                    .builder()
                    .type("file")
                    .id(file.getId())
                    .name(file.getName())
                    .build();
            nodeFileList.add(nodeFile);
        }

        return Node.generateNode(nodeDirsList, nodeFileList);
    }

    public Node getEnvelopeDirsForUser(Long userId) {
        List<NodeDir> rootList = new ArrayList<>();
        List<NodeDir> toRemove = new ArrayList<>();

        List<NodeDir> all = directoryService.findDirectoriesByUserId(userId)
                .stream()
                .map(e -> NodeDir
                        .builder()
                        .type("dir")
                        .id(e.getId())
                        .name(e.getName())
                        .parentId(e.getParentId())
                        .childrenDirs(new ArrayList<>())
                        .files(new ArrayList<>())
                        .build())
                .collect(Collectors.toList());

        for (NodeDir dir : all) {
            if (dir.getParentId() == null) {
                rootList.add(dir);
                toRemove.add(dir);
            }
        }

        all.removeAll(toRemove);
        toRemove.clear();

        List<NodeDir> currents = new LinkedList<>(rootList);
        List<NodeDir> next = new LinkedList<>();
        Iterator<NodeDir> iterator = currents.iterator();

        while (!all.isEmpty()) {

//            for (NodeDir curr : currents) {
            while (iterator.hasNext()) {
                NodeDir curr = iterator.next();
                for (NodeDir unknown : all) {
                    if (curr.getId().equals(unknown.getParentId())) {
                        curr.getChildrenDirs().add(unknown);

                        next.add(unknown);
                        toRemove.add(unknown);
                    }
                }
                all.removeAll(toRemove);
                toRemove.clear();
            }

            currents = next;
        }

        return process(userId, rootList);
    }

    public Node process(Long userId, List<NodeDir> nodeDirs) {
        List<NodeFile> topFiles = new ArrayList<>();
        Node node = Node.generateNode(nodeDirs, topFiles);

        List<File> files = new ArrayList<>(fileService.findAllFilesByUserId(userId));
        List<File> toRemove = new ArrayList<>();

        Queue<NodeDir> queue = new LinkedList<>(nodeDirs);

        for (File file : files) {
            if (file.getDirectoryId() == null) {
                topFiles.add(NodeFile.builder()
                        .type("file")
                        .id(file.getId())
                        .name(file.getName())
                        .build());
                toRemove.add(file);
            }
        }

        files.removeAll(toRemove);
        toRemove.clear();

        NodeDir nodeDir;
        while ((nodeDir = queue.poll()) != null) {

            for (File file : files) {
                if (nodeDir.getId().equals(nodeDir.getParentId())) {
                    nodeDir.getFiles().add(NodeFile.builder()
                            .type("file")
                            .id(file.getId())
                            .name(file.getName())
                            .build());

                    queue.addAll(nodeDir.getChildrenDirs());
                }
            }
        }

        return node;
    }
}
