package com.example.tuan3_tts_th04395.controller;

import com.example.tuan3_tts_th04395.entity.dto.TaskRequest;
import com.example.tuan3_tts_th04395.entity.dto.TaskResponse;
import com.example.tuan3_tts_th04395.entity.enums.TaskStatus;
import com.example.tuan3_tts_th04395.exception.ApiResponse;
import com.example.tuan3_tts_th04395.exception.CustomException;
import com.example.tuan3_tts_th04395.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task", description = "API quản lý task")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "Get all tasks",
            description = "Lấy danh sách tất cả task"
    )
    @GetMapping
    public ApiResponse<List<TaskResponse>> getAll() {
        return new ApiResponse<>(200, "Success", taskService.findAll());
    }

    @Operation(
            summary = "Get task by id",
            description = "Lấy chi tiết task theo task id"
    )
    @GetMapping("/{taskId}")
    public ApiResponse<TaskResponse> getById(
            @Parameter(description = "Task ID", example = "1")
            @PathVariable Integer taskId) {
        return new ApiResponse<>(200, "Success", taskService.findById(taskId));
    }

    @Operation(
            summary = "Get tasks by user",
            description = "Lấy tất cả task được gán cho user"
    )
    @GetMapping("/user/{userId}")
    public ApiResponse<List<TaskResponse>> getByUser(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Integer userId) {
        return new ApiResponse<>(200, "Success", taskService.findByUser(userId));
    }

    @Operation(
            summary = "Get tasks by project",
            description = "Lấy tất cả task của project"
    )
    @GetMapping("/project/{projectId}")
    public ApiResponse<List<TaskResponse>> getByProject(
            @Parameter(description = "Project ID", example = "1")
            @PathVariable Integer projectId) {
        return new ApiResponse<>(200, "Success", taskService.findByProject(projectId));
    }

    @Operation(
            summary = "Create new task",
            description = "Tạo task mới, mặc định status là TODO"
    )
    @PostMapping
    public ApiResponse<TaskResponse> create(@RequestBody @Valid TaskRequest request) {
        return new ApiResponse<>(201, "Task created", taskService.createTask(request));
    }

    @Operation(
            summary = "Assign task to user",
            description = "Gán task cho user theo user id"
    )
    @PutMapping("/{taskId}/assign/{userId}")
    public ApiResponse<TaskResponse> assignTask(
            @Parameter(description = "Task ID", example = "1")
            @PathVariable Integer taskId,
            @Parameter(description = "User ID", example = "2")
            @PathVariable Integer userId) {
        return new ApiResponse<>(200, "Assign task success", taskService.assignTask(taskId, userId));
    }

    @Operation(
            summary = "Update task status",
            description = "Cập nhật trạng thái task: TODO, IN_PROGRESS, REVIEW, DONE, CANCELLED"
    )
    @PutMapping("/{taskId}/status")
    public ApiResponse<TaskResponse> updateStatus(
            @Parameter(description = "Task ID", example = "1")
            @PathVariable Integer taskId,
            @Parameter(description = "Task status", example = "IN_PROGRESS")
            @RequestParam TaskStatus status) {
        return new ApiResponse<>(200, "Update status success", taskService.updateStatus(taskId, status));
    }

    @Operation(
            summary = "Get my tasks",
            description = "Lấy danh sách task của user đang đăng nhập"
    )
    @GetMapping("/my-tasks")
    public ApiResponse<List<TaskResponse>> myTasks(@Parameter(hidden = true) Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new CustomException("Unauthenticated");
        }

        return new ApiResponse<>(200, "Success", taskService.getMyTasks(auth.getName()));
    }
}