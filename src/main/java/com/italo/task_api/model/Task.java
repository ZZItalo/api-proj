package com.italo.task_api.model;

import com.italo.task_api.enums.TaskStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Table("tasks2")
public class Task {

    @Id
    private final long id;
    private final String title;
    private final String body;
    private final TaskStatus status;
    private final ZoneOffset zoneOffset;
    private final LocalDateTime creationDate;
    private final LocalDateTime lastUpdateDate;
    private final LocalDateTime dueDate;

    @PersistenceCreator
    public Task(
            long id,
            String title,
            String body,
            TaskStatus status,
            ZoneOffset zoneOffset,
            LocalDateTime creationDate,
            LocalDateTime lastUpdateDate,
            LocalDateTime dueDate
    ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.status = status;
        this.zoneOffset = zoneOffset;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.dueDate = dueDate;
    }

    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.body = builder.body;
        this.status = builder.status;
        this.zoneOffset = builder.zoneOffset;
        this.creationDate = builder.creationDate;
        this.lastUpdateDate = builder.lastUpdateDate;
        this.dueDate = builder.dueDate;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public static class Builder {
        private long id;
        private String title;
        private String body;
        private TaskStatus status;
        private ZoneOffset zoneOffset;
        private LocalDateTime creationDate;
        private LocalDateTime lastUpdateDate;
        private LocalDateTime dueDate;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder zoneOffset(ZoneOffset zoneOffset) {
            this.zoneOffset = zoneOffset;
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder lastUpdateDate(LocalDateTime lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
            return this;
        }

        public Builder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Task build() {
            return new Task(this);
        }

    }
}
