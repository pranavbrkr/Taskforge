package com.taskforge.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectResponse {
    private String id;
    private String name;
    private String key;
}
