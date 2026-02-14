package com.taskforge.project.service;

import com.taskforge.project.dto.CreateProjectRequest;
import com.taskforge.project.dto.ProjectResponse;
import com.taskforge.project.dto.UpdateProjectRequest;
import com.taskforge.project.exception.DuplicateProjectKeyException;
import com.taskforge.project.model.Project;
import com.taskforge.project.repo.JpaProjectRepository;
import com.taskforge.project.repo.ProjectRepository;
import com.taskforge.task.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProjectService {

    private final JpaProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(JpaProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public ProjectResponse create(CreateProjectRequest request) {
        String key = request.getKey();

        if(projectRepository.findByKey(key).isPresent()) {
            throw new DuplicateProjectKeyException(key);
        }

        Project project = new Project(
                UUID.randomUUID().toString(),
                request.getName(),
                key
        );

        projectRepository.save(project);
        return toResponse(project);
    }

    public ProjectResponse getById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found: " + id));
        return toResponse(project);
    }

    public List<ProjectResponse> getAll() {
        return projectRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void delete(String id) {
        boolean exists = projectRepository.existsById(id);
        if (!exists) {
            throw new NoSuchElementException("Project not found: " + id);
        }

        taskRepository.deleteByProjectId(id);
        projectRepository.deleteById(id);
    }

    public ProjectResponse update(String id, UpdateProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found: " + id));

        if (request.getName() != null) {
            project.setName(request.getName());
        }

        projectRepository.save(project);
        return toResponse(project);
    }

    public ProjectResponse toResponse(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getKey());
    }
}
