package ru.cloudfilestorage.cloudfilestorage.domain.mapper;

import io.minio.Result;
import io.minio.messages.Item;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.MinioObject;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MinioMapper {

    public static List<MinioObject> convert(Iterable<Result<Item>> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(itemResult -> {
                    try {
                        String path = itemResult.get().objectName();
                        if (
                                path != null && !path.isBlank()) {
                            String name = Paths.get(path).getFileName().toString();

                            return MinioObject
                                    .builder()
                                    .name(name)
                                    .path(path)
                                    .isDirectory(itemResult.get().isDir())
                                    .build();
                        }
                        return null;
                    } catch (Exception e) {
                        throw new RuntimeException("Unable to convert Minio object", e);
                    }
                })
                .collect(Collectors.toList());
    }

}
