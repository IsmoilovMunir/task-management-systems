package com.task.management.validation;

import com.task.management.entity.Role;
import com.task.management.entity.User;
import com.task.management.repository.RoleRepository;
import com.task.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class Validation {
    private final RoleRepository repository;
    private final UserRepository userRepository;

    public Role findByIdRole(Long roleId) {
        return repository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
    public User findByIdUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
