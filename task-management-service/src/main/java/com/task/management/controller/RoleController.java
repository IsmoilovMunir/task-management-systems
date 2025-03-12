package com.task.management.controller;

import com.task.management.dto.RoleDto;
import com.task.management.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Role Management", description = "Operations related to role management")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Create a new role", description = "Creates a new role in the system")
    @ApiResponse(responseCode = "201", description = "Role successfully created")
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @Operation(summary = "Get role by ID", description = "Retrieves a specific role by its ID")
    @ApiResponse(responseCode = "200", description = "Role successfully retrieved")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto roleDto = roleService.getRoleById(id);
        return ResponseEntity.ok(roleDto);
    }

    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of roles")
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Delete role by ID", description = "Deletes a specific role by its ID")
    @ApiResponse(responseCode = "204", description = "Role successfully deleted")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            roleService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @Operation(summary = "Update role", description = "Updates an existing role by ID")
    @ApiResponse(responseCode = "200", description = "Role successfully updated")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updaterRole(@PathVariable Long id, @Valid @RequestBody RoleDto roleDto) {
        RoleDto updateRole = roleService.updateRole(id, roleDto);
        return ResponseEntity.ok(updateRole);
    }
}
