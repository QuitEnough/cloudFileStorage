package ru.cloudfilestorage.cloudfilestorage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.cloudfilestorage.cloudfilestorage.entity.User;
import ru.cloudfilestorage.cloudfilestorage.dto.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.dto.UserResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    UserResponse toUserResponse(User user);
    User toUser(UserRequest userRequest);

}
