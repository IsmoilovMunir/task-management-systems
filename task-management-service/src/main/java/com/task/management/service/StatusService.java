package com.task.management.service;

import com.task.management.dto.StatusDto;
import com.task.management.entity.Status;
import com.task.management.mapper.StatusMapper;
import com.task.management.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StatusService {
    private final StatusRepository statusRepository;
    public final StatusMapper statusMapper;

    public StatusDto createStatus(StatusDto statusDto) {
        Status status = statusMapper.toEntity(statusDto);
        statusRepository.save(status);
        return statusMapper.toDto(status);
    }

    public StatusDto getStatusById(Long statusId) {
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("" + statusId));
        return statusMapper.toDto(status);
    }

}
