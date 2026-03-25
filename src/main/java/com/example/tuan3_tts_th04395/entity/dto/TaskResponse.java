package com.example.tuan3_tts_th04395.entity.dto;


import com.example.tuan3_tts_th04395.entity.Task;
import com.example.tuan3_tts_th04395.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskResponse {
    private Integer taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private String priority;
    private LocalDate dueDate;
    private Instant createdAt;

    private Integer projectId;
    private String projectName;

    private Integer assigneeId;
    private String assigneeUsername;

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getProject().getProjectId(),
                task.getProject().getName(),
                task.getAssignee().getUserId(),
                task.getAssignee().getUsername()
        );
    }
}
