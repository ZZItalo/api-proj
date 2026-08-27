package com.italo.task_api.repository;

import com.italo.task_api.enums.TaskStatus;
import com.italo.task_api.model.Task;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task,Long> {

    @Query("""
            SELECT t.id, t.title, tb.body_text AS body, t.status, t.zone_offset, t.creation_date,t.last_update_date, t.due_date
            FROM tasks AS t
            INNER JOIN task_body AS tb
            ON t.id = tb.id
            WHERE (:id IS NULL OR t.id = :id)
            AND (:taskStatus IS NULL OR t.status = :taskStatus)
            AND (:year IS NULL OR YEAR(t.creation_date) = :year)
            AND (:dayOfWeek IS NULL OR DAYNAME(t.creation_date) = :dayOfWeek)
            AND (:date1 IS NULL OR t.creation_date >= :date1)
            AND (:date2 IS NULL OR t.creation_date < :date2)
            """)
    List<Task> findTasks(Long id, TaskStatus taskStatus, Integer year, DayOfWeek dayOfWeek, LocalDateTime date1, LocalDateTime date2);

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
