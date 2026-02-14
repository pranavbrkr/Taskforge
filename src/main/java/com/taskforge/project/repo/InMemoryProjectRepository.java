package com.taskforge.project.repo;

import com.taskforge.project.model.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryProjectRepository implements ProjectRepository {
    private final Map<String, Project> store = new ConcurrentHashMap<>();
    private final Map<String, String> keyToId = new ConcurrentHashMap<>();

    @Override
    public Project save(Project project) {
        store.put(project.getId(), project);
        keyToId.put(project.getKey(), project.getId());
        return project;
    }

    @Override
    public Optional<Project> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Project> findByKey(String key) {
        String id = keyToId.get(key);
        if (id == null)
            return Optional.empty();
        return findById(id);
    }

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(String id) {
        Project removed = store.remove(id);
        if (removed == null)
            return false;

        keyToId.remove(removed.getKey());
        return true;
    }
}
