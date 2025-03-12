package com.task.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {

    private Long id;

    @NotBlank(message = "Role name should not be blank")
    @Size(max = 50, message = "Role name should not exceed 50 characters")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Role name should not be blank") @Size(max = 50, message = "Role name should not exceed 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Role name should not be blank") @Size(max = 50, message = "Role name should not exceed 50 characters") String name) {
        this.name = name;
    }
}

