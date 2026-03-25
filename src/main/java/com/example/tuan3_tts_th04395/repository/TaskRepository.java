package com.example.tuan3_tts_th04395.repository;

import com.example.tuan3_tts_th04395.entity.Task;
import com.example.tuan3_tts_th04395.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByAssignee_UserId(Integer userId);

    List<Task> findByProject_ProjectId(Integer projectId);

    List<Task> findByStatus(TaskStatus status);
}
