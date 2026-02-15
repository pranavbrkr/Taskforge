package com.taskforge.task.dto;

import com.taskforge.task.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    private String id;
    private String title;
    private TaskStatus status;
}
