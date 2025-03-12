package com.task.management.mapper;

import com.task.management.entity.Status;
import com.task.management.entity.User;
import com.task.management.repository.StatusRepository;
import com.task.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskMapperHelper {


    private final UserRepository userRepository;
    private final StatusRepository statusRepository;


    @Named("mapUser")
    public User mapUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Named("mapStatus")
    public Status mapStatus(Long statusId) {
        if (statusId == null) {
            throw new IllegalArgumentException("Status ID cannot be null");
        }
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found with ID: " + statusId));
    }
}
