package com.task.management.controller;

import com.task.management.dto.TaskDto;
import com.task.management.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task Management", description = "Operations related to task management")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Create a new task", description = "Creates a new task in the system")
    @ApiResponse(responseCode = "201", description = "Task successfully created")
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        TaskDto createTask = taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTask);
    }

    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tasks")
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Update a task", description = "Updates an existing task by ID")
    @ApiResponse(responseCode = "200", description = "Task successfully updated")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskId, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a specific task by its ID")
    @ApiResponse(responseCode = "200", description = "Task successfully retrieved")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        TaskDto taskDto = taskService.getTaskById(id);
        return ResponseEntity.ok(taskDto);
    }

    @Operation(summary = "Get tasks by author", description = "Retrieves tasks created by a specific author")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks by author")
    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<TaskDto>> getTasksByAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByAuthor(authorId, page, size));
    }

    @Operation(summary = "Get tasks by assignee", description = "Retrieves tasks assigned to a specific assignee")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks by assignee")
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<Page<TaskDto>> getTasksByAssignee(
            @PathVariable Long assigneeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByAssignee(assigneeId, page, size));
    }
}
