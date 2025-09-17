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

    @GetMapping("")
    public ResponseEntity<List<TaskDto>> getAllUserTasks() {
        return ResponseEntity.ok(taskService.getAllTasksByUserId());
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskDto> getTaskByIdAndUserId(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskByIdAndUserId(taskId));
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskDto taskDto) {
        return ResponseEntity.ok(taskService.createTask(taskDto));
    }

    @PutMapping("/")
    public ResponseEntity<TaskDto> updateTaskByIdAndUserId(@RequestBody @Valid TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTaskByIdAndUserId(taskDto));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskByIdAndUserId(@PathVariable Long taskId) {
        taskService.deleteTaskByIdAndUserId(taskId);
        return ResponseEntity.noContent().build();
    }

}
