package com.bahaa.todo.service;

import com.bahaa.todo.model.dto.TaskDto;

import java.util.List;

public interface TaskService {
    public TaskDto createTask(TaskDto taskDto);
    public TaskDto getTaskByIdAndUserId(Long taskId);
    public TaskDto updateTaskByIdAndUserId(TaskDto taskDto);
    public void deleteTaskByIdAndUserId(Long id);
    public List<TaskDto> getAllTasksByUserId();

}
