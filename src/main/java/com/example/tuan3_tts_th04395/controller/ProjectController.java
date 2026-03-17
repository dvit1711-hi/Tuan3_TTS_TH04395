package com.example.tuan3_tts_th04395.controller;

import com.example.tuan3_tts_th04395.entity.Project;
import com.example.tuan3_tts_th04395.exception.ApiResponse;
import com.example.tuan3_tts_th04395.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ApiResponse<Project> createProject(@RequestBody Project project,
                                              Authentication auth){

        return new ApiResponse<>(
                201,
                "Project created",
                projectService.create(project, auth.getName())
        );
    }
}