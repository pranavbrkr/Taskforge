package com.taskforge.project.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProjectRequest {
    @Size(min = 3, max = 100, message = "name must be 3 to 100 characters")
    private String name;
}
