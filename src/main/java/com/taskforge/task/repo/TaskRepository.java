package com.taskforge.task.repo;

import com.taskforge.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByProjectId(String projectId);

    long deleteByProjectId(String projectId);

    @Query("select t from Task t join fetch t.project where t.project.id = :projectId")
    List<Task> findByProjectIdWithProject(@Param("projectId") String projectId);
}
