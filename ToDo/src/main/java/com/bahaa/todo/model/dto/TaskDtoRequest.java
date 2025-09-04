package com.bahaa.todo.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDtoRequest {

    @NotBlank
    private String title;

    @Size(max = 500)
    private String description;

    @NotBlank
    private String status;

    @NotBlank
    private String priority;

    @FutureOrPresent
    private LocalDate dueDate;

}
