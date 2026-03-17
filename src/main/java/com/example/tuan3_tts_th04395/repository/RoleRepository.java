package com.example.tuan3_tts_th04395.repository;

import com.example.tuan3_tts_th04395.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String user);
}
