package com.example.tuan3_tts_th04395.service;

import com.example.tuan3_tts_th04395.entity.Role;
import com.example.tuan3_tts_th04395.entity.User;
import com.example.tuan3_tts_th04395.entity.dto.LoginRequest;
import com.example.tuan3_tts_th04395.entity.dto.RegisterRequest;
import com.example.tuan3_tts_th04395.repository.RoleRepository;
import com.example.tuan3_tts_th04395.repository.UserRepository;
import com.example.tuan3_tts_th04395.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request){

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

    public String login(LoginRequest request){

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Password incorrect");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}