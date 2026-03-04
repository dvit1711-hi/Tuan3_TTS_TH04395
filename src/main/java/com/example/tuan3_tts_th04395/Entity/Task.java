package com.example.tuan3_tts_th04395.Entity;

import jakarta.persistence.*;
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
    @Column(name = "task_id", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Size(max = 20)
    @NotNull
    @Column(name = "priority", nullable = false, length = 20)
    private String priority;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignee_id", nullable = false)
    private com.example.tuan3_tts_th04395.Entity.User assignee;

    @ColumnDefault("getdate()")
    @Column(name = "created_at")
    private Instant createdAt;

}