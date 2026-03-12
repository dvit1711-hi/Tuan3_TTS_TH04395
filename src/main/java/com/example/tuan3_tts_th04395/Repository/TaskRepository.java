package com.example.tuan3_tts_th04395.Repository;

import com.example.tuan3_tts_th04395.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByAssignee_UserId(Integer userId);

    List<Task> findByProject_ProjectId(Integer projectId);

    List<Task> findByStatus(String status);
}
