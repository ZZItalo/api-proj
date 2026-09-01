package com.italo.task_api.repository;

import com.italo.task_api.enums.TaskStatus;
import com.italo.task_api.model.Task;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task,Long> {

    @Query("""
            SELECT t.id,
            t.title,
            t.body,
            t.status,
            t.zone_offset,
            t.creation_date,
            t.last_update_date,
            t.due_date
            FROM tasks AS t
            WHERE (:status IS NULL OR t.status = :status)
            AND (:year IS NULL OR YEAR(t.creation_date) = :year)
            AND (:dayOfWeek IS NULL OR DAYNAME(t.creation_date) = :dayOfWeek)
            AND (:date1 IS NULL OR t.creation_date >= :date1)
            AND (:date2 IS NULL OR t.creation_date < :date2)
            """)
    List<Task> findTasks(TaskStatus status,
                         Integer year,
                         DayOfWeek dayOfWeek,
                         LocalDateTime date1,
                         LocalDateTime date2
    );
}
