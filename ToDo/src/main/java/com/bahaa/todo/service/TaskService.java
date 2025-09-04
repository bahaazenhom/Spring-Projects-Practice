package com.bahaa.todo.service;

import com.bahaa.todo.model.dto.TaskDtoRequest;
import com.bahaa.todo.model.dto.TaskDto;

import java.util.List;

public interface TaskService {
    public TaskDto createTask(TaskDto taskDto);
    public TaskDto getTaskById(Long id);
    public TaskDto updateTask(TaskDto taskDto);
    public void deleteTask(Long id);
    public List<TaskDto> getAllTasksById(Long userId);

}
