package com.example.tuan3_tts_th04395.Service;

import com.example.tuan3_tts_th04395.Entity.Task;
import com.example.tuan3_tts_th04395.Repository.ProjectRepository;
import com.example.tuan3_tts_th04395.Repository.TaskRepository;
import com.example.tuan3_tts_th04395.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<Task> findByUser(Integer userId) {
        return taskRepository.findByAssignee_UserId(userId);
    }

    public List<Task> findByProject(Integer projectId) {
        return taskRepository.findByProject_ProjectId(projectId);
    }

    public Task create(Task task) {

        Integer userId = task.getAssignee().getUserId();
        Integer projectId = task.getProject().getProjectId();

        task.setAssignee(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        task.setProject(projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found")));

        return taskRepository.save(task);
    }
}
