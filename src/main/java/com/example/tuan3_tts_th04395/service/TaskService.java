package com.example.tuan3_tts_th04395.service;

import com.example.tuan3_tts_th04395.entity.Project;
import com.example.tuan3_tts_th04395.entity.Task;
import com.example.tuan3_tts_th04395.entity.User;
import com.example.tuan3_tts_th04395.entity.dto.TaskRequest;
import com.example.tuan3_tts_th04395.entity.dto.TaskResponse;
import com.example.tuan3_tts_th04395.entity.enums.TaskStatus;
import com.example.tuan3_tts_th04395.exception.CustomException;
import com.example.tuan3_tts_th04395.repository.ProjectRepository;
import com.example.tuan3_tts_th04395.repository.TaskRepository;
import com.example.tuan3_tts_th04395.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse findById(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found"));
        return TaskResponse.fromEntity(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findByUser(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        return taskRepository.findByAssignee_UserId(userId)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findByProject(Integer projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found"));

        return taskRepository.findByProject_ProjectId(projectId)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new CustomException("Project not found"));

        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new CustomException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority().trim().toUpperCase());
        task.setDueDate(request.getDueDate());
        task.setProject(project);
        task.setAssignee(assignee);
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(Instant.now());

        Task saved = taskRepository.save(task);
        return TaskResponse.fromEntity(saved);
    }

    @Transactional
    public TaskResponse assignTask(Integer taskId, Integer userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        task.setAssignee(user);

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    @Transactional
    public TaskResponse updateStatus(Integer taskId, TaskStatus newStatus) {
        if (newStatus == null) {
            throw new CustomException("Status cannot be null");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found"));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new CustomException("Task already completed. Cannot update.");
        }

        task.setStatus(newStatus);

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getMyTasks(String principal) {
        User user = userRepository.findByUsername(principal)
                .or(() -> userRepository.findByEmail(principal))
                .orElseThrow(() -> new CustomException("User not found: " + principal));

        return taskRepository.findByAssignee_UserId(user.getUserId())
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }
}
