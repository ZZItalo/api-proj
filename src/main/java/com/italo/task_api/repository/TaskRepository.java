package com.italo.task_api.repository;

import com.italo.task_api.enums.TaskStatus;
import com.italo.task_api.model.Task;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface TaskRepository extends CrudRepository<Task,Long> {

    @Modifying
    @Query("""
            INSERT INTO tasks (
            title,
            status,
            zone_offset,
            creation_date,
            last_update_date,
            due_date
            )
            VALUES (
            :title,
            :status,
            :zoneOffset,
            :creationDate,
            :lastUpdateDate,
            :dueDate
            );
            """)
    void insertTask(
            String title,
            TaskStatus status,
            String zoneOffset,
            LocalDateTime creationDate,
            LocalDateTime lastUpdateDate,
            LocalDateTime dueDate
    );

    @Modifying
    @Query("""
            INSERT INTO task_body (id, body_text)
            VALUES (LAST_INSERT_ID(), :body);
            """)
    void insertTaskBody(String body);
}
