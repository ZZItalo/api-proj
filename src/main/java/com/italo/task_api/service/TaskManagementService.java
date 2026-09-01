package com.italo.task_api.service;

import com.italo.task_api.enums.TaskStatus;
import com.italo.task_api.exception.ResourceNotFoundException;
import com.italo.task_api.model.Task;
import com.italo.task_api.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TaskManagementService {

    private final TaskRepository taskRepo;

    public TaskManagementService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task findTaskById(Long id) {
        return taskRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Theres no such entry in the database with Id: " + id)
        );
    }

    public List<Task> findTasks(TaskStatus taskStatus,
                                Integer year,
                                DayOfWeek dayOfWeek,
                                LocalDate date1,
                                LocalDate date2) {

        LocalDateTime startDateTime = (date1 != null) ? date1.atStartOfDay() : null;

        LocalDateTime endDateTime = (date2 != null) ? date2.plusDays(1).atStartOfDay() : null;

        return taskRepo.findTasks(
                taskStatus,
                year,
                dayOfWeek,
                startDateTime,
                endDateTime
        );
    }

    public Task updateTask(Long id,
                           String title,
                           String body,
                           TaskStatus status,
                           LocalDateTime dueDate) {

        Task toUpdate = taskRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Theres no such entry in the database with Id: " + id)
        );

        boolean isTitleNullOrEmpty = (title == null) || title.isEmpty();

        Task t = new Task.Builder()
                .id(toUpdate.getId())
                .title(isTitleNullOrEmpty ? toUpdate.getTitle() : title)
                .body((body==null) ? toUpdate.getBody() : body)
                .status((status == null) ? toUpdate.getStatus() : status)
                .zoneOffset(toUpdate.getZoneOffset())
                .creationDate(toUpdate.getCreationDate())
                .lastUpdateDate(LocalDateTime.now())
                .dueDate((dueDate == null) ? toUpdate.getDueDate() : dueDate)
                .build();

        return taskRepo.save(t);
    }

    public Long createTask(Task task) {

        return taskRepo.save(task).getId();
    }

    public void deleteTask(Long id) {

        taskRepo.deleteById(id);
    }
}
