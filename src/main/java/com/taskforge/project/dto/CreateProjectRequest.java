package com.taskforge.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProjectRequest {

    @NotBlank(message = "name is required")
    @Size(min = 3, max = 10, message = "name must be 3 to 100 characters")
    private String name;

    @NotBlank(message = "key is required")
    @Pattern(regexp = "^[A-Z]{2,10}$", message = "key must be 2-10 uppercase letters (e.g. TF, TASKFORGE)")
    private String key;
}
