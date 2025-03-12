package com.task.management.mapper;

import com.task.management.dto.TaskDto;
import com.task.management.entity.Task;
import com.task.management.entity.User;
import com.task.management.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = TaskMapperHelper.class)
public interface TaskMapper {


    @Mapping(source = "authorId", target = "author", qualifiedByName = "mapUser")
    @Mapping(source = "assigneeId", target = "assignee", qualifiedByName = "mapUser")
    @Mapping(source = "statusId", target = "status", qualifiedByName = "mapStatus")

    Task toEntity(TaskDto dto);

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "status.id", target = "statusId")
    TaskDto toDto(Task task);


}
