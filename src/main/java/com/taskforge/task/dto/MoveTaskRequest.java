package com.taskforge.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MoveTaskRequest {

    @NotBlank(message = "targetProjectId is required")
    private String targetProjectId;
}
