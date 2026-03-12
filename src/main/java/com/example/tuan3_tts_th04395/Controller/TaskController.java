package com.example.tuan3_tts_th04395.Controller;

import com.example.tuan3_tts_th04395.Entity.Task;
import com.example.tuan3_tts_th04395.Entity.enums.TaskStatus;
import com.example.tuan3_tts_th04395.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Task> getByUser(@PathVariable Integer userId) {
        return taskService.findByUser(userId);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getByProject(@PathVariable Integer projectId) {
        return taskService.findByProject(projectId);
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
    @PutMapping("/{taskId}/assign/{userId}")
    public Task assignTask(@PathVariable Integer taskId,
                           @PathVariable Integer userId) {

        return taskService.assignTask(taskId, userId);
    }
    @PutMapping("/{taskId}/status")
    public Task updateStatus(@PathVariable Integer taskId,
                             @RequestParam TaskStatus status) {

        return taskService.updateStatus(taskId, status);
    }
}
