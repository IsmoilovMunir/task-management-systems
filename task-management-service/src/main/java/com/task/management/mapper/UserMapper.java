package com.task.management.mapper;

import com.task.management.dto.UserDto;
import com.task.management.entity.Role;
import com.task.management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);

    @Mapping(source = "role.id", target = "roleId")
    UserDto toDto(User user);

    @Mapping(target = "role", ignore = true)
        // Игнорируем role при преобразовании DTO в сущность
    User toEntity(UserDto userDto);

    default User toEntityWithRole(UserDto userDto, Role role) {
        User user = toEntity(userDto);
        user.setRole(role); // Устанавливаем роль
        return user;
    }
}
