package com.task.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDto {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    private String description;

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be less than 50 characters")
    private Long statusId;

    @NotBlank(message = "Priority cannot be blank")
    @Size(max = 50, message = "Priority must be less than 50 characters")
    private String priority;

    @NotNull(message = "Author ID cannot be null")
    private Long authorId;

    @NotNull(message = "Assignee ID cannot be null")
    private Long assigneeId;

    public @NotNull(message = "Assignee ID cannot be null") Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(@NotNull(message = "Assignee ID cannot be null") Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public @NotNull(message = "Author ID cannot be null") Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(@NotNull(message = "Author ID cannot be null") Long authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Priority cannot be blank") @Size(max = 50, message = "Priority must be less than 50 characters") String getPriority() {
        return priority;
    }

    public void setPriority(@NotBlank(message = "Priority cannot be blank") @Size(max = 50, message = "Priority must be less than 50 characters") String priority) {
        this.priority = priority;
    }

    public @NotBlank(message = "Status cannot be blank") @Size(max = 50, message = "Status must be less than 50 characters") Long getStatusId() {
        return statusId;
    }

    public void setStatusId(@NotBlank(message = "Status cannot be blank") @Size(max = 50, message = "Status must be less than 50 characters") Long statusId) {
        this.statusId = statusId;
    }

    public @NotBlank(message = "Title cannot be blank") @Size(max = 255, message = "Title must be less than 255 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title cannot be blank") @Size(max = 255, message = "Title must be less than 255 characters") String title) {
        this.title = title;
    }
}

