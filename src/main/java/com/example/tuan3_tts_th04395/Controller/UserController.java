package com.example.tuan3_tts_th04395.Controller;

import com.example.tuan3_tts_th04395.Entity.User;
import com.example.tuan3_tts_th04395.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // GET all
    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    // GET by id
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // POST
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT
    @PutMapping("/{id}")
    public User update(@PathVariable Integer id,
                             @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
