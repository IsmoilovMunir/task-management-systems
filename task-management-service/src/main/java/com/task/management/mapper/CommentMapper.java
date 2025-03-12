package com.task.management.mapper;

import com.task.management.dto.CommentDto;
import com.task.management.dto.TaskDto;
import com.task.management.dto.UserDto;
import com.task.management.entity.Comment;
import com.task.management.entity.Task;
import com.task.management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",  uses = CommentMapperHelper.class)
public interface CommentMapper {

    @Mapping(source = "taskId", target = "task", qualifiedByName = "mapTask")
    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUser")
    Comment toEntity(CommentDto dto);

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "user.id", target = "userId")
    CommentDto toDto(Comment comment);

}
