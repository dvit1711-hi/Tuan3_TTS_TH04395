package com.example.tuan3_tts_th04395.service;

import com.example.tuan3_tts_th04395.entity.User;
import com.example.tuan3_tts_th04395.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Lấy tất cả user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy theo ID
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Tạo user mới
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(user);
    }

    // Update user
    public User updateUser(Integer id, User updatedUser) {

        User user = getUserById(id);

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

}
