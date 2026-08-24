package com.italo.task_api.controller;

import com.italo.task_api.dto.TaskDto;
import com.italo.task_api.model.Task;
import com.italo.task_api.service.TaskManagementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskManagementService taskMgrSrvc;

    public TaskController(TaskManagementService taskMgrSrvc) {
        this.taskMgrSrvc = taskMgrSrvc;
    }

    @PostMapping
    public void createTask(@RequestBody TaskDto dto) {

        Task task = new Task.Builder()
                .title(dto.getTitle())
                .body(dto.getBody())
                .status(dto.getStatus())
                .zoneOffset(dto.getZoneOffset())
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .dueDate(dto.getDueDate())
                .build();

        taskMgrSrvc.create(task);
    }
}
