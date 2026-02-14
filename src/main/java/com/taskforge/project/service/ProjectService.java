package com.taskforge.project.service;

import com.taskforge.project.dto.CreateProjectRequest;
import com.taskforge.project.dto.ProjectResponse;
import com.taskforge.project.exception.DuplicateProjectKeyException;
import com.taskforge.project.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProjectService {
//    thread-safe map since controllers/services are singletons
    private final Map<String, Project> store = new ConcurrentHashMap<>();
    private final Map<String, String> keyToId = new ConcurrentHashMap<>();

    public ProjectResponse create(CreateProjectRequest request) {
        String key = request.getKey();

        if(keyToId.containsKey(key)) {
            throw new DuplicateProjectKeyException(key);
        }

        String id = UUID.randomUUID().toString();

        Project project = new Project(
                id,
                request.getName(),
                request.getKey()
        );

        store.put(id, project);
        keyToId.put(key, id);

        return toResponse(project);
    }

    public ProjectResponse getById(String id) {
        Project project = store.get(id);
        if (project == null) {
            throw new NoSuchElementException("Project not found: " + id);
        }
        return toResponse(project);
    }

    public List<ProjectResponse> getAll() {
        return store.values().stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(String id) {
        Project removed = store.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("Project not found: " + id);
        }
    }

    public ProjectResponse toResponse(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getKey());
    }
}
