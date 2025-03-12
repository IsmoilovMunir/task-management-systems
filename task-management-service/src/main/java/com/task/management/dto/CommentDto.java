package com.task.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    @NotBlank(message = "comment  text should not be blank")
    private String text;

    @NotNull(message = "Task ID should not be null")
    private Long taskId;

    @NotNull(message = "User ID should not be null")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Task ID should not be null") Long getTaskId() {
        return taskId;
    }

    public void setTaskId(@NotNull(message = "Task ID should not be null") Long taskId) {
        this.taskId = taskId;
    }

    public @NotBlank(message = "comment  text should not be blank") String getText() {
        return text;
    }

    public void setText(@NotBlank(message = "comment  text should not be blank") String text) {
        this.text = text;
    }

    public @NotNull(message = "User ID should not be null") Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "User ID should not be null") Long userId) {
        this.userId = userId;
    }
}
