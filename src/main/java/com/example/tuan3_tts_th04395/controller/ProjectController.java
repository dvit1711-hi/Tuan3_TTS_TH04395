package com.example.tuan3_tts_th04395.controller;

import com.example.tuan3_tts_th04395.entity.Project;
import com.example.tuan3_tts_th04395.exception.ApiResponse;
import com.example.tuan3_tts_th04395.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project", description = "API quản lý project")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(
            summary = "Create new project",
            description = "Manager tạo project mới"
    )
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ApiResponse<Project> createProject(@RequestBody Project project,
                                              @Parameter(hidden = true) Authentication auth) {

        return new ApiResponse<>(
                201,
                "Project created",
                projectService.create(project, auth.getName())
        );
    }
}