package com.example.tuan3_tts_th04395;

import com.example.tuan3_tts_th04395.entity.Project;
import com.example.tuan3_tts_th04395.entity.Task;
import com.example.tuan3_tts_th04395.entity.User;
import com.example.tuan3_tts_th04395.exception.CustomException;
import com.example.tuan3_tts_th04395.repository.ProjectRepository;
import com.example.tuan3_tts_th04395.repository.TaskRepository;
import com.example.tuan3_tts_th04395.repository.UserRepository;
import com.example.tuan3_tts_th04395.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User projectOwner;
    private User anotherUser;
    private Project project;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        projectOwner = new User();
        projectOwner.setUserId(1);

        anotherUser = new User();
        anotherUser.setUserId(2);

        project = new Project();
        project.setProjectId(1);
        project.setOwner(projectOwner);

        task = new Task();
        task.setTaskId(1);
        task.setProject(project);
    }

    @Test
    void createTask_success() {
        // Assignee phải có trước khi tạo
        task.setAssignee(projectOwner);

        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(userRepository.findById(projectOwner.getUserId())).thenReturn(Optional.of(projectOwner));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(task);

        assertNotNull(result);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void assignTask_success() {
        task.setProject(project);

        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));
        when(userRepository.findById(projectOwner.getUserId())).thenReturn(Optional.of(projectOwner));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.assignTask(task.getTaskId(), projectOwner.getUserId());

        assertEquals(projectOwner.getUserId(), result.getAssignee().getUserId());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_projectNotFound() {
        Project missingProject = new Project();
        missingProject.setProjectId(99);
        task.setProject(missingProject);

        when(projectRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> taskService.createTask(task));
    }

    @Test
    void assignTask_userNotInProject() {
        task.setProject(project);

        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));
        when(userRepository.findById(anotherUser.getUserId())).thenReturn(Optional.of(anotherUser));

        assertThrows(CustomException.class,
                () -> taskService.assignTask(task.getTaskId(), anotherUser.getUserId()));
    }

    // Edge case: assignee = null khi tạo task
    @Test
    void createTask_assigneeNull() {
        task.setAssignee(null);

        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));

        assertThrows(CustomException.class, () -> taskService.createTask(task));
    }

    // Edge case: assign task với taskId không tồn tại
    @Test
    void assignTask_taskNotFound() {
        when(taskRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> taskService.assignTask(999, projectOwner.getUserId()));
    }

}