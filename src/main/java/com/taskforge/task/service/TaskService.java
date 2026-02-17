package com.taskforge.task.service;

import com.taskforge.audit.model.AuditLog;
import com.taskforge.audit.repo.AuditLogRepository;
import com.taskforge.project.model.Project;
import com.taskforge.project.repo.JpaProjectRepository;
import com.taskforge.task.Task;
import com.taskforge.task.dto.*;
import com.taskforge.task.model.TaskStatus;
import com.taskforge.task.repo.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final JpaProjectRepository projectRepository;
    private final AuditLogRepository auditLogRepository;

    public TaskService(
            TaskRepository taskRepository,
            JpaProjectRepository projectRepository,
            AuditLogRepository auditLogRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.auditLogRepository = auditLogRepository;
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

    @Transactional(readOnly = true)
    public TaskResponse getById(String taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + taskId));

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getProject().getKey()
        );
    }

    @Transactional
    public TaskResponse update(String taskId, UpdateTaskRequest request) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + taskId));

        if (request.getTitle() != null)
            task.setTitle(request.getTitle());

        if (request.getStatus() != null)
            task.setStatus(request.getStatus());

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getProject().getKey()
        );
    }

    @Transactional
    public void delete(String taskId) {
        boolean exists = taskRepository.existsById(taskId);

        if (!exists)
            throw new NoSuchElementException("Task not found: " + taskId);

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public TaskResponse moveTask(String taskId, MoveTaskRequest request) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + taskId));

        var targetProject = projectRepository.findById(request.getTargetProjectId())
                .orElseThrow(() -> new NoSuchElementException("Project not found: " + request.getTargetProjectId()));

        var oldProjectKey = task.getProject().getKey();
        var newProjectKey = targetProject.getKey();

        task.setProject(targetProject);

        var log = new AuditLog(
                UUID.randomUUID().toString(),
                "TASK_MOVED",
                "Task " + task.getId() + " moved from " + oldProjectKey + " to " + newProjectKey,
                Instant.now()
        );

        auditLogRepository.save(log);

        return new TaskResponse(task.getId(), task.getTitle(), task.getStatus(), newProjectKey);
    }

    @Transactional(readOnly = true)
    public PageResponse<TaskListRow> listByProjectPaged(String projectId, TaskStatus status, String q, Pageable pageable) {
        if (!projectRepository.existsById(projectId)) {
            throw new NoSuchElementException("Project not found: " + projectId);
        }

        Page<TaskListRow> page = taskRepository.findTaskRowsByProjectId(projectId, status, q, pageable);

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public List<Task> debugEntities(String projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NoSuchElementException("Project not found: " + projectId);
        }
        return taskRepository.findByProjectId(projectId);
    }
}
