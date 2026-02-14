package com.taskforge.task;

import com.taskforge.project.model.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    private String id;

    @Column(nullable = false, length = 200)
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
