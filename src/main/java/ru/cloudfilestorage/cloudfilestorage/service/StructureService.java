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
import java.util.stream.Collectors;

@Service
public class StructureService {

    private final FileServiceImpl fileService;

    private final DirectoryServiceImpl directoryService;

    @Autowired
    public StructureService(FileServiceImpl fileService, DirectoryServiceImpl directoryService) {
        this.fileService = fileService;
        this.directoryService = directoryService;
    }

    public Map<Long, List<File>> doStructureFiles(List<File> fileList, Set<Directory> directorySet) {
        Map<Long, List<File>> structuredMap = new HashMap<>();

        structuredMap.put(null,
                fileList.stream().filter(file -> file.getDirectoryId() == null).toList());

        List<Long> directoryIdList = directorySet.stream().map(Directory::getId).toList();
        for (Long directoryId : directoryIdList) {
            structuredMap.put(directoryId,
                    fileList.stream().filter(file -> file.getDirectoryId().equals(directoryId)).toList());
        }

        return structuredMap;
    }

    public Node getInfoForCertainDir(Long directoryId) {

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

//    public Node getInfoForCertainDir(Long directoryId) {
//
//        List<Directory> dirs = directoryService.findAllDirectoriesInCertainDir(directoryId);
//        List<NodeDir> nodeDirsList = new ArrayList<>();
//
//        for (Directory directory : dirs) {
//            NodeDir nodeDir = NodeDir
//                    .builder()
//                    .type("dir")
//                    .id(directory.getId())
//                    .name(directory.getName())
//                    .envelopeDirs(dfsForEnvelopeDirs(dirs, directory))
//                    .build();
//            nodeDirsList.add(nodeDir);
//        }
//
//        List<File> files = fileService.findAllFilesInCertainDir(directoryId);
//        List<NodeFile> nodeFileList = new ArrayList<>();
//
//        for (File file : files) {
//            NodeFile nodeFile = NodeFile
//                    .builder()
//                    .type("file")
//                    .id(file.getId())
//                    .name(file.getName())
//                    .uuid(file.getUuid())
//                    .build();
//            nodeFileList.add(nodeFile);
//        }
//
//        return Node.generateNode(nodeDirsList, nodeFileList);
//    }
//
//    private List<NodeDir> dfsForEnvelopeDirs(List<Directory> dirsList, Directory directory) {
//        Stack<NodeDir> stack = new Stack<>();
//        boolean[] visited = new boolean[dirsList.size()];
//
//        NodeDir current = NodeDir
//                .builder()
//                .type("dir")
//                .id(directory.getId())
//                .name(directory.getName())
//                .build();
//
//        stack.push(current);
//        while (!stack.isEmpty()) {
//            current = stack.pop();
//            if (!)
//        }
//
//        return List.of();
//    }
//
//    private static NodeDir dfs(List<Directory> dirsList, Directory directory) {
//        dirsList.stream().sorted(Comparator.comparing(Directory::getParentId)).toList();
//        for (Directory dir : dirsList) {
//
//        }
//        Stack<Directory> stack = new Stack<>();
//        stack.push(directory);
//
//        int maxId = dirsList.stream().filter(dir -> dir.getId() < )
//        while (!stack.isEmpty()) {
//            Directory node = stack.pop();
//        }
//    }
//    private static NodeDir bfs(List<Directory> dirsList, Directory directory) {
//        Queue<NodeDir> queue = new ArrayDeque<>();
//
//        int dirsListSize = dirsList.size();
//        int count = 0;
//
//
//
//        for (Directory d : dirsList) {
//            if (d.getParentId() == null) {
//                NodeDir nodeDir = NodeDir
//                        .builder()
//                        .type("dir")
//                        .id(d.getId())
//                        .name(d.getName())
//                        .build();
//                queue.add(nodeDir);
//                count++;
//            }
//        }
//        while (!queue.isEmpty()) {
//            NodeDir nodeDir = queue.remove();
//            if (count < dirsListSize) {
//                for (int i = 1; i <= dirsList.size(); i++) {
//
//                }
//            }
//        }
//        while (Comparator.comparing(Directory::getParentId) == null) {
//            Directory rootDir = dirsList.stream().findFirst().get();
//            NodeDir nodeDir = NodeDir
//                    .builder()
//                    .type("dir")
//                    .id(rootDir.getId())
//                    .name(rootDir.getName())
//                    .build();
//            queue.add(nodeDir);
//        }
//
//    }
//
//    private void bfsForCertainDir(List<Directory> dirsList, Long dirId) {
//        for (Directory d : dirsList) {
//            List<Directory> dirsForId = dirsList.stream().filter(dir -> dir.getParentId().equals(dirId)).toList();
//        }
//    }

}
