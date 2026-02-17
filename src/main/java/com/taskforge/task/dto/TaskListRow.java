package com.taskforge.task.dto;

import com.taskforge.task.model.TaskStatus;

import java.time.Instant;

public interface TaskListRow {
    String getId();
    String getTitle();
    TaskStatus getStatus();
    String getProjectKey();
    Instant getCreatedAt();
    Instant getUpdatedAt();
}
