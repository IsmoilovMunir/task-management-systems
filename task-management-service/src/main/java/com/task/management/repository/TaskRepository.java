package com.task.management.repository;

import com.task.management.entity.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Найти все задачи автора
    Page<Task> findByAuthorId(Long authorId, Pageable pageable);

    // Найти все задачи исполнителя
    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);
}
