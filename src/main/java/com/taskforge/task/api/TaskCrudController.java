package com.taskforge.task.api;

import com.taskforge.task.dto.TaskResponse;
import com.taskforge.task.dto.UpdateTaskRequest;
import com.taskforge.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskCrudController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public TaskResponse getById(@PathVariable String taskId) {
        return taskService.getById(taskId);
    }

    @PatchMapping("/{taskId}")
    public TaskResponse update(@PathVariable String taskId, @Valid @RequestBody UpdateTaskRequest request) {
        return taskService.update(taskId, request);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String taskId) {
        taskService.delete(taskId);
    }
}
