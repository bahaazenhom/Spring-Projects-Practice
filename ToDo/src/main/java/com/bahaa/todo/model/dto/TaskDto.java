package com.bahaa.todo.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    @NotBlank
    private String title;

    @Size(max = 500)
    private String description;

    @NotBlank
    private String status;

    @NotBlank
    private String priority;

    @FutureOrPresent(message = "The due date must be in the present or future")
    private LocalDate dueDate;

    private CategoryDto category;

}
