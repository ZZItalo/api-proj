package com.italo.task_api.controller;

import com.italo.task_api.dto.TaskDto;
import com.italo.task_api.enums.TaskStatus;
import com.italo.task_api.model.Task;
import com.italo.task_api.service.TaskManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskManagementService taskMgrService;

    public TaskController(TaskManagementService taskMgrService) {
        this.taskMgrService = taskMgrService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long id) {

        Task t = taskMgrService.findTaskById(id);

        return ResponseEntity.ok().body(t);
    }

    @GetMapping
    public ResponseEntity<List<Task>> findTasks(@RequestParam(required = false) TaskStatus taskStatus,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) DayOfWeek dayOfWeek,
                                  @RequestParam(required = false) LocalDate date1,
                                  @RequestParam(required = false) LocalDate date2) {

        List<Task> lt =  taskMgrService.findTasks(taskStatus,year,dayOfWeek,date1,date2);

        return ResponseEntity.ok().body(lt);
    }

    @PatchMapping
    public ResponseEntity<Task> updateTask(@RequestParam Long id,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) String body,
                                           @RequestParam(required = false) TaskStatus status,
                                           @RequestParam(required = false) LocalDateTime dueDate) {

        Task t = taskMgrService.updateTask(id, title, body, status, dueDate);

        return ResponseEntity.ok().body(t);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDto dto) {

        Task task = new Task.Builder()
                .title(dto.getTitle())
                .body(dto.getBody())
                .status(dto.getStatus())
                .zoneOffset(dto.getZoneOffset())
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .dueDate(dto.getDueDate())
                .build();

        // Returns the created instance with an ID
        task = taskMgrService.createTask(task);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(uri).body(task);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTaskById(@RequestParam Long id) {

        taskMgrService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
