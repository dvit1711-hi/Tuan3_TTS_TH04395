package com.example.tuan3_tts_th04395;

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
import com.example.tuan3_tts_th04395.service.TaskService;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_success() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Task test");
        request.setDescription("Mo ta");
        request.setPriority("HIGH");
        request.setDueDate(LocalDate.now().plusDays(3));
        request.setProjectId(1);
        request.setAssigneeId(2);

        User assignee = new User();
        assignee.setUserId(2);
        assignee.setUsername("user2");

        User owner = new User();
        owner.setUserId(1);
        owner.setUsername("manager");

        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");
        project.setOwner(owner);

        Task savedTask = new Task();
        savedTask.setTaskId(100);
        savedTask.setTitle(request.getTitle());
        savedTask.setDescription(request.getDescription());
        savedTask.setPriority(request.getPriority());
        savedTask.setDueDate(request.getDueDate());
        savedTask.setStatus(TaskStatus.TODO);
        savedTask.setCreatedAt(Instant.now());
        savedTask.setProject(project);
        savedTask.setAssignee(assignee);

        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(userRepository.findById(2)).thenReturn(Optional.of(assignee));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse result = taskService.createTask(request);

        assertNotNull(result);
        assertEquals(100, result.getTaskId());
        assertEquals("Task test", result.getTitle());
        assertEquals(TaskStatus.TODO, result.getStatus());
        assertEquals(1, result.getProjectId());
        assertEquals(2, result.getAssigneeId());

        verify(projectRepository).findById(1);
        verify(userRepository).findById(2);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_projectNotFound() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Task test");
        request.setDescription("Mo ta");
        request.setPriority("HIGH");
        request.setDueDate(LocalDate.now().plusDays(3));
        request.setProjectId(99);
        request.setAssigneeId(2);

        when(projectRepository.findById(99)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> taskService.createTask(request));

        assertEquals("Project not found", ex.getMessage());
        verify(projectRepository).findById(99);
        verify(userRepository, never()).findById(any());
        verify(taskRepository, never()).save(any());
    }

    @Test
    void createTask_userNotFound() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Task test");
        request.setDescription("Mo ta");
        request.setPriority("HIGH");
        request.setDueDate(LocalDate.now().plusDays(3));
        request.setProjectId(1);
        request.setAssigneeId(999);

        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");

        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> taskService.createTask(request));

        assertEquals("User not found", ex.getMessage());
        verify(projectRepository).findById(1);
        verify(userRepository).findById(999);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void assignTask_success() {
        User oldAssignee = new User();
        oldAssignee.setUserId(1);
        oldAssignee.setUsername("oldUser");

        User newAssignee = new User();
        newAssignee.setUserId(2);
        newAssignee.setUsername("newUser");

        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");

        Task task = new Task();
        task.setTaskId(10);
        task.setTitle("Task 10");
        task.setDescription("Desc");
        task.setPriority("HIGH");
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setProject(project);
        task.setAssignee(oldAssignee);

        when(taskRepository.findById(10)).thenReturn(Optional.of(task));
        when(userRepository.findById(2)).thenReturn(Optional.of(newAssignee));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse result = taskService.assignTask(10, 2);

        assertNotNull(result);
        assertEquals(10, result.getTaskId());
        assertEquals(2, result.getAssigneeId());
        assertEquals("newUser", result.getAssigneeUsername());

        verify(taskRepository).findById(10);
        verify(userRepository).findById(2);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void assignTask_taskNotFound() {
        when(taskRepository.findById(999)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> taskService.assignTask(999, 1));

        assertEquals("Task not found", ex.getMessage());
        verify(taskRepository).findById(999);
        verify(userRepository, never()).findById(any());
        verify(taskRepository, never()).save(any());
    }

    @Test
    void assignTask_userNotFound() {
        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");

        User oldAssignee = new User();
        oldAssignee.setUserId(1);
        oldAssignee.setUsername("oldUser");

        Task task = new Task();
        task.setTaskId(10);
        task.setProject(project);
        task.setAssignee(oldAssignee);

        when(taskRepository.findById(10)).thenReturn(Optional.of(task));
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> taskService.assignTask(10, 999));

        assertEquals("User not found", ex.getMessage());
        verify(taskRepository).findById(10);
        verify(userRepository).findById(999);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void updateStatus_success() {
        User assignee = new User();
        assignee.setUserId(1);
        assignee.setUsername("user1");

        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");

        Task task = new Task();
        task.setTaskId(1);
        task.setTitle("Task A");
        task.setStatus(TaskStatus.TODO);
        task.setPriority("HIGH");
        task.setProject(project);
        task.setAssignee(assignee);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse result = taskService.updateStatus(1, TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        verify(taskRepository).findById(1);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateStatus_taskAlreadyDone() {
        User assignee = new User();
        assignee.setUserId(1);
        assignee.setUsername("user1");

        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");

        Task task = new Task();
        task.setTaskId(1);
        task.setStatus(TaskStatus.DONE);
        task.setProject(project);
        task.setAssignee(assignee);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        CustomException ex = assertThrows(CustomException.class,
                () -> taskService.updateStatus(1, TaskStatus.REVIEW));

        assertEquals("Task already completed. Cannot update.", ex.getMessage());
        verify(taskRepository).findById(1);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void getMyTasks_success_findByUsername() {
        User user = new User();
        user.setUserId(2);
        user.setUsername("user2");

        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");

        Task task = new Task();
        task.setTaskId(1);
        task.setTitle("Task mine");
        task.setStatus(TaskStatus.TODO);
        task.setPriority("HIGH");
        task.setProject(project);
        task.setAssignee(user);

        when(userRepository.findByUsername("user2")).thenReturn(Optional.of(user));
        when(taskRepository.findByAssignee_UserId(2)).thenReturn(List.of(task));

        List<TaskResponse> result = taskService.getMyTasks("user2");

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getTaskId());
        assertEquals(2, result.get(0).getAssigneeId());

        verify(userRepository).findByUsername("user2");
        verify(taskRepository).findByAssignee_UserId(2);
    }

    @Test
    void getMyTasks_success_findByEmail() {
        User user = new User();
        user.setUserId(3);
        user.setUsername("user3");
        user.setEmail("user3@gmail.com");

        Project project = new Project();
        project.setProjectId(1);
        project.setName("Project A");

        Task task = new Task();
        task.setTaskId(2);
        task.setTitle("Task email");
        task.setStatus(TaskStatus.TODO);
        task.setPriority("LOW");
        task.setProject(project);
        task.setAssignee(user);

        when(userRepository.findByUsername("user3@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("user3@gmail.com")).thenReturn(Optional.of(user));
        when(taskRepository.findByAssignee_UserId(3)).thenReturn(List.of(task));

        List<TaskResponse> result = taskService.getMyTasks("user3@gmail.com");

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getTaskId());
        assertEquals(3, result.get(0).getAssigneeId());

        verify(userRepository).findByUsername("user3@gmail.com");
        verify(userRepository).findByEmail("user3@gmail.com");
        verify(taskRepository).findByAssignee_UserId(3);
    }

    @Test
    void getMyTasks_userNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("unknown")).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> taskService.getMyTasks("unknown"));

        assertTrue(ex.getMessage().contains("User not found"));
        verify(userRepository).findByUsername("unknown");
        verify(userRepository).findByEmail("unknown");
        verify(taskRepository, never()).findByAssignee_UserId(any());
    }
}