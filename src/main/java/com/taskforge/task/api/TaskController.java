package com.taskforge.task.api;

import com.taskforge.task.Task;
import com.taskforge.task.dto.CreateTaskRequest;
import com.taskforge.task.dto.TaskResponse;
import com.taskforge.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskResponse create(@PathVariable String projectId, @Valid @RequestBody CreateTaskRequest request) {
        return taskService.create(projectId, request);
    }

    @GetMapping
    public List<TaskResponse> list(@PathVariable String projectId) {
        return taskService.listByProject(projectId);
    }

    @GetMapping("/debug-entity")
    public List<Task> debugEntity(@PathVariable String projectId) {
        return taskService.debugEntities(projectId);
    }
}
