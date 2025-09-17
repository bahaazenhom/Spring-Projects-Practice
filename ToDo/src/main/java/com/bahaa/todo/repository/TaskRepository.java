package com.bahaa.todo.repository;

import com.bahaa.todo.entity.Task;
import com.bahaa.todo.entity.User;
import com.bahaa.todo.model.dto.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserId(Long userId);
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);

    Long user(User user);
}
