package com.example.tuan3_tts_th04395.controller;

import com.example.tuan3_tts_th04395.entity.Project;
import com.example.tuan3_tts_th04395.entity.dto.LoginRequest;
import com.example.tuan3_tts_th04395.entity.dto.RegisterRequest;
import com.example.tuan3_tts_th04395.exception.ApiResponse;
import com.example.tuan3_tts_th04395.service.AuthService;
import com.example.tuan3_tts_th04395.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResController {
    private final AuthService authService;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){

        authService.register(request);

        return "Register success";
    }
    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

        return authService.login(request);
    }

}
