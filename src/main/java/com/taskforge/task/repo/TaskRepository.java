package com.taskforge.task.repo;

import com.taskforge.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByProjectId(String projectId);

    long deleteByProjectId(String projectId);
}
