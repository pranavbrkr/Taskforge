package com.taskforge.task.dto;

import com.taskforge.task.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotBlank
    @Size(min = 3, max = 200)
    private String title;

    @NotNull
    private TaskStatus status;
}
