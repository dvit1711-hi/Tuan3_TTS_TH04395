package com.example.tuan3_tts_th04395.Entity;

import com.example.tuan3_tts_th04395.Entity.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 150)
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Lob
    @Size(max = 500, message = "Description too long")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TaskStatus status;

    @Size(max = 20)
    @Column(name = "priority", nullable = false, length = 20)
    private String priority;

    @Column(name = "due_date")
    @Future(message = "Deadline must be in the future")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @ColumnDefault("getdate()")
    @Column(name = "created_at")
    private Instant createdAt;

}