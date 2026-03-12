package com.example.tuan3_tts_th04395.Service;

import com.example.tuan3_tts_th04395.Entity.Project;
import com.example.tuan3_tts_th04395.Entity.Task;
import com.example.tuan3_tts_th04395.Entity.User;
import com.example.tuan3_tts_th04395.Entity.enums.TaskStatus;
import com.example.tuan3_tts_th04395.Exception.CustomException;
import com.example.tuan3_tts_th04395.Repository.ProjectRepository;
import com.example.tuan3_tts_th04395.Repository.TaskRepository;
import com.example.tuan3_tts_th04395.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    public List<Task> findAllById(Integer taskId) {
        return taskRepository.findAllById(Collections.singleton(taskId));
    }

    public List<Task> findByUser(Integer userId) {
        List<Task> tasks = taskRepository.findByAssignee_UserId(userId);
        if (tasks.isEmpty()) {
            throw new CustomException( "User has no tasks or user not found");
        }
        return tasks;
    }

    public List<Task> findByProject(Integer projectId) {

        List<Task> tasks = taskRepository.findByProject_ProjectId(projectId);
        if (tasks.isEmpty()) {
            throw new CustomException( "Project has no tasks or project not found");
        }
        return tasks;
    }

//    public Task create(Task task) {
//
//        Integer userId = task.getAssignee().getUserId();
//        Integer projectId = task.getProject().getProjectId();
//
//        task.setAssignee(userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found")));
//
//        task.setProject(projectRepository.findById(projectId)
//                .orElseThrow(() -> new RuntimeException("Project not found")));
//
//        return taskRepository.save(task);
//    }
public Task createTask(Task task) {

    Integer projectId = task.getProject().getProjectId();

    Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new CustomException("Project not found"));

    task.setProject(project);
//    if (task.getDueDate() != null &&
//            task.getDueDate().isBefore(LocalDate.now())) {
//
//        throw new CustomException("Deadline must be greater than current date");
//    }

    // lấy assignee
    Integer userId = task.getAssignee().getUserId();

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException("User not found"));

    task.setAssignee(user);

    task.setStatus(TaskStatus.TODO);

    return taskRepository.save(task);
}
    public Task assignTask(Integer taskId, Integer userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        // kiểm tra user thuộc project
        if (!task.getProject().getOwner().getUserId().equals(userId)) {
            throw new CustomException("User does not belong to this project");
        }

        task.setAssignee(user);

        return taskRepository.save(task);
    }
    public Task updateStatus(Integer taskId, TaskStatus newStatus) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found"));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new CustomException("Task already completed. Cannot update.");
        }

        task.setStatus(newStatus);

        return taskRepository.save(task);
    }
}
