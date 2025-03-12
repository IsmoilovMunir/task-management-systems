package com.task.management.service;

import com.task.management.dto.RoleDto;
import com.task.management.entity.Role;
import com.task.management.mapper.RoleMapper;
import com.task.management.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository repository;
    private final RoleMapper roleMapper;

    public RoleDto createRole(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        repository.save(role);
        return roleMapper.toDto(role);
    }

    public RoleDto getRoleById(Long roleId) {
        Role role = repository.findById(roleId).orElseThrow(() ->
                new RuntimeException("Role not found"));
        return roleMapper.toDto(role);
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roles = repository.findAll();
        return roles.stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Role not found with ID" + id);
        }
        repository.deleteById(id);
    }

    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role role = repository.findById(id).orElseThrow(() -> new RuntimeException("Role not found with ID" + id));
        roleMapper.updateRole(roleDto, role);
        Role updateRole = repository.save(role);
        return roleMapper.toDto(updateRole);
    }

}
