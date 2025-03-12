package com.task.management.mapper;

import com.task.management.dto.RoleDto;
import com.task.management.dto.StatusDto;
import com.task.management.entity.Role;
import com.task.management.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    @Mapping(target = "id", ignore = true)
    void updateRole(RoleDto roleDto, @MappingTarget Role role);

    StatusDto toDto(Status status);

    Status toEntity(StatusDto statusDto);
}
