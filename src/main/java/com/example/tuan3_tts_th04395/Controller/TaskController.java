package com.example.tuan3_tts_th04395.Controller;

import com.example.tuan3_tts_th04395.Entity.Task;
import com.example.tuan3_tts_th04395.Entity.enums.TaskStatus;
import com.example.tuan3_tts_th04395.Exception.ApiResponse;
import com.example.tuan3_tts_th04395.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ApiResponse<List<Task>> getAll(){

        List<Task> tasks = taskService.findAll();

        return new ApiResponse<>(200,"Success",tasks);
    }
//    @GetMapping("/user/{userId}")
//    public ApiResponse<List<Task>> getAllByTaskId(@PathVariable Integer taskId){
//
//        List<Task> tasks = taskService.findAll();
//
//        return new ApiResponse<>(200,"Success",tasks);
//    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Task>> getByUser(@PathVariable Integer userId) {
        List<Task> tasks = taskService.findByUser(userId);

        return new ApiResponse<>(205,"Success",tasks);
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<Task>> getByProject(@PathVariable Integer projectId) {
        List<Task> tasks = taskService.findByProject(projectId);
        return new ApiResponse<>(206,"Success",tasks);
    }

//    @PostMapping
//    public Task create(@RequestBody Task task) {
//        return taskService.create(task);
//    }
@PostMapping
public ApiResponse<Task> create(@RequestBody @Valid Task task){

    Task newTask = taskService.createTask(task);

    return new ApiResponse<>(201,"Task created",newTask);
}
    @PutMapping("/{taskId}/assign/{userId}")
    public ApiResponse<Task> assignTask(@PathVariable Integer taskId,
                           @PathVariable Integer userId) {

        Task task = taskService.assignTask(taskId, userId);
        return new ApiResponse<>(202,"put assign ok",task);
    }
    @PutMapping("/{taskId}/status")
    public ApiResponse<Task> updateStatus(@PathVariable Integer taskId,
                             @RequestParam TaskStatus status) {

        Task task = taskService.updateStatus(taskId, status);
        return new ApiResponse<>(203,"put updateStatus ok",task);
    }

}
