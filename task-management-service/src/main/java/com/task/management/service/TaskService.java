package com.task.management.service;

import com.task.management.dto.CommentDto;
import com.task.management.dto.TaskDto;
import com.task.management.entity.Task;
import com.task.management.mapper.CommentMapper;
import com.task.management.mapper.TaskMapper;
import com.task.management.mapper.TaskMapperHelper;
import com.task.management.repository.CommentRepository;
import com.task.management.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskMapperHelper taskMapperHelper;

    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        return taskMapper.toDto(task);

    }

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        // Обновляем существующую задачу
        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskMapperHelper.mapStatus(taskDto.getStatusId()));  // Маппинг статуса
        existingTask.setAuthor(taskMapperHelper.mapUser(taskDto.getAuthorId()));    // Маппинг автора
        existingTask.setAssignee(taskMapperHelper.mapUser(taskDto.getAssigneeId())); // Маппинг исполнителя

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDto(updatedTask);
    }

    public Page<TaskDto> getTasksByAuthor(Long authorId, int page, int size) {
        Page<Task> tasks = taskRepository.findByAuthorId(authorId, (Pageable) PageRequest.of(page, size));
        return tasks.map(taskMapper::toDto);
    }

    public Page<TaskDto> getTasksByAssignee(Long assigneeId, int page, int size) {
        Page<Task> tasks = taskRepository.findByAssigneeId(assigneeId, (Pageable) PageRequest.of(page, size));
        return tasks.map(taskMapper::toDto);
    }

    public List<CommentDto> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(commentMapper::toDto) // Используем маппер для преобразования комментариев
                .toList();
    }
}
