package com.example.tuan3_tts_th04395.controller;

import com.example.tuan3_tts_th04395.entity.dto.LoginRequest;
import com.example.tuan3_tts_th04395.entity.dto.RegisterRequest;
import com.example.tuan3_tts_th04395.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API đăng ký và đăng nhập")
public class AuthResController {

    private final AuthService authService;

    @Operation(
            summary = "Register new account",
            description = "Đăng ký tài khoản mới"
    )
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "Register success";
    }

    @Operation(
            summary = "Login",
            description = "Đăng nhập và trả về JWT token"
    )
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}