package com.taskforge.project.repo;

import com.taskforge.project.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);

    Optional<Project> findById(String id);

    Optional<Project> findByKey(String key);

    List<Project> findAll();

    boolean deleteById(String id);
}
