package com.taskforge.project.repo;

import com.taskforge.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findByKey(String key);
}
