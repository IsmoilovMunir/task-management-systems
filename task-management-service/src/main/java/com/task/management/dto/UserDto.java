package com.task.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Email should not be blank")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be a valid email address")
    @Size(max = 100, message = "Email should not exceed 100 characters")
    private String email;

    @NotBlank(message = "Password should not be blank")
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 255, message = "Password should be between 8 and 255 characters long")
    private String password;

    @NotNull(message = "Role ID should not be null")
    private Long roleId;

    public @NotBlank(message = "Email should not be blank") @NotEmpty(message = "Email should not be empty") @Email(message = "Email should be a valid email address") @Size(max = 100, message = "Email should not exceed 100 characters") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email should not be blank") @NotEmpty(message = "Email should not be empty") @Email(message = "Email should be a valid email address") @Size(max = 100, message = "Email should not exceed 100 characters") String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Password should not be blank") @NotEmpty(message = "Password should not be empty") @Size(min = 8, max = 255, message = "Password should be between 8 and 255 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password should not be blank") @NotEmpty(message = "Password should not be empty") @Size(min = 8, max = 255, message = "Password should be between 8 and 255 characters long") String password) {
        this.password = password;
    }

    public @NotNull(message = "Role ID should not be null") Long getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotNull(message = "Role ID should not be null") Long roleId) {
        this.roleId = roleId;
    }

}


