package com.taskforge.task.service;

import com.taskforge.project.model.Project;
import com.taskforge.project.repo.JpaProjectRepository;
import com.taskforge.task.Task;
import com.taskforge.task.dto.CreateTaskRequest;
import com.taskforge.task.dto.TaskResponse;
import com.taskforge.task.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final JpaProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, JpaProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public TaskResponse create(String projectId, CreateTaskRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found: " + projectId));

        Task task = new Task(
                UUID.randomUUID().toString(),
                request.getTitle(),
                request.getStatus(),
                project
        );

        var saved =  taskRepository.save(task);

        return new TaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getStatus(),
                project.getKey()
        );
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> listByProject(String projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NoSuchElementException("Project not found: " + projectId);
        }

        return taskRepository.findByProjectIdWithProject(projectId).stream()
                .map(t -> new TaskResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getStatus(),
                        t.getProject().getKey()
                ))
                .toList();
    }

    public List<Task> debugEntities(String projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NoSuchElementException("Project not found: " + projectId);
        }
        return taskRepository.findByProjectId(projectId);
    }
}
