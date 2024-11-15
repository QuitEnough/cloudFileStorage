package ru.cloudfilestorage.cloudfilestorage.service;

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

@Service
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
                    .uuid(file.getUuid())
                    .build();
            nodeFileList.add(nodeFile);
        }

        return Node.generateNode(nodeDirsList, nodeFileList);
    }

    public List<Node> getAllDataForUser(Long userId) {

        List<NodeDir> all = directoryService.findDirectoriesByUserId(userId)
                .stream()
                .map(e -> NodeDir
                        .builder()
                        .type("dir")
                        .id(e.getId())
                        .name(e.getName())
                        .build())
                .toList();

        List<NodeDir> rootList = new LinkedList<>();
        List<NodeDir> currentList = new LinkedList<>();
        List<NodeDir> nextList = new LinkedList<>();

        for (Directory d : all) {
            if (d.getParentId() == null) {
                currentList.add(d);
                all.remove(d);
            }
        }
        for (int i = 0; i < currentList.size(); i++) {
            NodeDir nodeDir = NodeDir
                    .builder()
                    .type("dir")
                    .id(currentList.get(i).getId())
//                    .envelopeDirs()   --- как вставить лист, если я еще не знаю значений
                    .build();
        }
        List<Node> nodeList = new LinkedList<>();

        long maxRef = all
                .stream()
                .max(Comparator.comparing(Directory::getParentId))
                .get()
                .getParentId();
        for (int i = 1; i <= maxRef; i++) {
            for (Directory d : all) {
                if (d.getParentId().equals(1L)) {
                    currentList.add(d);
                }
            }
        }

        int count = all.size();
        for (Directory d : all) {
            for (int i = 1; i <= all.size(); i++) {

            }
        }

        return List.of();
    }

    private List<NodeDir> bfs(List<Directory> dirs) {
        for (int i = 0; i < dirs.size(); i++) {
            long minParentId = dirs.stream().min(Comparator.comparing(Directory::getParentId)).get().getParentId();

        }
        return List.of();
    }

}
