package com.example.tuan3_tts_th04395.service;

import com.example.tuan3_tts_th04395.entity.Project;
import com.example.tuan3_tts_th04395.entity.User;
import com.example.tuan3_tts_th04395.repository.ProjectRepository;
import com.example.tuan3_tts_th04395.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    private UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project create(Project project, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        project.setOwner(user);
        return projectRepository.save(project);
    }

    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
}
