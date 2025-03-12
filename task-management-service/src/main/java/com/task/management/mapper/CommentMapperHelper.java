package com.task.management.mapper;

import com.task.management.entity.Task;
import com.task.management.entity.User;
import com.task.management.repository.TaskRepository;
import com.task.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CommentMapperHelper {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Named("mapTask")
    public Task mapTask(Long taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        return taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
    }

    @Named("mapUser")
    public User mapUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
}
