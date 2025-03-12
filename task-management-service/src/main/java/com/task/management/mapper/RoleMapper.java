package com.task.management.mapper;

import com.task.management.dto.RoleDto;
import com.task.management.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    @Mapping(target = "id", ignore = true)
    void updateRole(RoleDto roleDto, @MappingTarget Role role);
    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}
