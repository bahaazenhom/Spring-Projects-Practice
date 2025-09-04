package com.bahaa.todo.control;

import com.bahaa.todo.model.dto.TaskDto;
import com.bahaa.todo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tasks")
@RestController
@Validated
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskDto>> getAllUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getAllTasksById(userId));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskDto taskDto) {
        return ResponseEntity.ok(taskService.createTask(taskDto));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody @Valid TaskDto taskDto) {
        taskDto.setId(taskId);
        return ResponseEntity.ok(taskService.updateTask(taskDto));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }



}
