package com.taskforge.task.dto;

import com.taskforge.task.model.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTaskRequest {

    @Size(min = 3, max = 200, message = "title must be 3 to 200 characters")
    private String title;

    private TaskStatus status;
}
