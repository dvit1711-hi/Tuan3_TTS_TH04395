package com.example.tuan3_tts_th04395.controller;

import com.example.tuan3_tts_th04395.entity.User;
import com.example.tuan3_tts_th04395.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "API quản lý user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users", description = "Lấy danh sách tất cả user")
    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by id", description = "Lấy thông tin user theo id")
    @GetMapping("/{id}")
    public User getById(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Create new user", description = "Tạo user mới")
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Update user", description = "Cập nhật thông tin user")
    @PutMapping("/{id}")
    public User update(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Integer id,
            @RequestBody User user) {
        return userService.updateUser(id, user);
    }

//    @Operation(summary = "Delete user", description = "Xóa user theo id")
//    @DeleteMapping("/{id}")
//    public String delete(
//            @Parameter(description = "User ID", example = "1")
//            @PathVariable Integer id) {
//        userService.deleteUser(id);
//        return "User deleted successfully";
//    }
}