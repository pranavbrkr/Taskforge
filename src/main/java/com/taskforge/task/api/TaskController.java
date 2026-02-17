package com.taskforge.task.api;

import com.taskforge.task.Task;
import com.taskforge.task.dto.CreateTaskRequest;
import com.taskforge.task.dto.PageResponse;
import com.taskforge.task.dto.TaskListRow;
import com.taskforge.task.dto.TaskResponse;
import com.taskforge.task.model.TaskStatus;
import com.taskforge.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

//    @GetMapping
//    public List<TaskResponse> list(@PathVariable String projectId) {
//        return taskService.listByProject(projectId);
//    }

    @GetMapping
    public PageResponse<TaskListRow> list(
            @PathVariable String projectId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String q,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        return taskService.listByProjectPaged(projectId, status, q, pageable);
    }

    @GetMapping("/debug-entity")
    public List<Task> debugEntity(@PathVariable String projectId) {
        return taskService.debugEntities(projectId);
    }
}
