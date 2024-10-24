package ru.cloudfilestorage.cloudfilestorage.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserRequest;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.UserResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    UserResponse toUserResponse(User user);
    UserRequest toUserRequest(User user);
    User toUser(UserRequest userRequest);

}
