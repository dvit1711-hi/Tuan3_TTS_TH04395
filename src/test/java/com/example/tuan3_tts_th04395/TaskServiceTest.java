package com.example.tuan3_tts_th04395;

import com.example.tuan3_tts_th04395.entity.Project;
import com.example.tuan3_tts_th04395.entity.Task;
import com.example.tuan3_tts_th04395.entity.User;
import com.example.tuan3_tts_th04395.repository.ProjectRepository;
import com.example.tuan3_tts_th04395.repository.TaskRepository;
import com.example.tuan3_tts_th04395.repository.UserRepository;
import com.example.tuan3_tts_th04395.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TaskService taskService;
    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createTask_success() {
        Task task = new Task();
        Project project = new Project();
        project.setProjectId(1);
        task.setProject(project);
        when(projectRepository.findById(1))
                .thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);
        when(taskRepository.findById(1))
                .thenReturn(Optional.of(task));

        Task result = taskService.createTask(task);

        assertNotNull(result);
        verify(taskRepository).save(task);
    }
    @Test
    void assignTask_success() {
        Task task = new Task();
        task.setTaskId(1);
        Project project = new Project();
        User user = new User();
        user.setUserId(1);
        project.setOwner(user);
        task.setProject(project);
        when(taskRepository.findById(1))
                .thenReturn(Optional.of(task));
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);
        Task result = taskService.assignTask(1,1);
        assertEquals(1,result.getAssignee().getUserId());
    }


}
