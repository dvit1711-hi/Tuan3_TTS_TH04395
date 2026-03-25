package com.example.tuan3_tts_th04395.entity.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 150, message = "Title max length is 150")
    private String title;

    @Size(max = 500, message = "Description too long")
    private String description;

    @NotBlank(message = "Priority cannot be empty")
    @Pattern(
            regexp = "LOW|MEDIUM|HIGH|URGENT",
            message = "Priority must be LOW, MEDIUM, HIGH or URGENT"
    )
    private String priority;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    @NotNull(message = "Project id is required")
    private Integer projectId;

    @NotNull(message = "Assignee id is required")
    private Integer assigneeId;
}
