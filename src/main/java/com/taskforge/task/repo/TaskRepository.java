package com.taskforge.task.repo;

import com.taskforge.task.Task;
import com.taskforge.task.dto.TaskListRow;
import com.taskforge.task.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByProjectId(String projectId);

    long deleteByProjectId(String projectId);

    @Query("select t from Task t join fetch t.project where t.project.id = :projectId order by t.createdAt desc")
    List<Task> findByProjectIdWithProject(@Param("projectId") String projectId);

    @Query("""
        select
            t.id as id,
            t.title as title,
            t.status as status,
            p.key as projectKey,
            t.createdAt as createdAt,
            t.updatedAt as updatedAt
        from Task t
        join t.project p
        where p.id = :projectId
            and (: status is null or t.status = :status)
            and (:q is null or lower(t.title) like lower(concat('%', :q, '%')))
        """)
    Page<TaskListRow> findTaskRowsByProjectId(
            @Param("projectId") String projectId,
            @Param("status")TaskStatus status,
            @Param("q") String q,
            Pageable pageable
    );
}
