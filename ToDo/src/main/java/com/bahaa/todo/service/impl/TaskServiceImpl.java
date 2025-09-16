package com.bahaa.todo.service.impl;

import com.bahaa.todo.entity.Task;
import com.bahaa.todo.exception.BusinessLogicException;
import com.bahaa.todo.mapper.TaskMapper;
import com.bahaa.todo.model.dto.TaskDto;
import com.bahaa.todo.repository.TaskRepository;
import com.bahaa.todo.repository.UserRepository;
import com.bahaa.todo.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private static final Logger logger = Logger.getLogger(TaskServiceImpl.class.getName());

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }


    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toTaskEntity(taskDto);
        task.setCreatedAt(java.time.Instant.now());
        task.setUpdatedAt(java.time.Instant.now());
        task.setUser(userRepository.getUserById(12L).orElseThrow(()-> new BusinessLogicException("User with id 12 does not exist.")));
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskDto(savedTask);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()-> new BusinessLogicException("Task with id " + id + " does not exist."));
        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        Task task = taskRepository.findById(taskDto.getId()).orElseThrow(() -> new BusinessLogicException("Task with id " + taskDto.getId() + " does not exist."));
        taskMapper.updateTaskFromDto(taskDto, task);
        task.setUpdatedAt(java.time.Instant.now());

        return taskMapper.toTaskDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new BusinessLogicException("Task with id " + id + " does not exist."));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskDto> getAllTasksById(Long userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks.stream().map(taskMapper::toTaskDto).toList();
    }
}
