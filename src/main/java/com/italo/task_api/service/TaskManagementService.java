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
import java.util.Objects;


@Service
@Transactional
public class TaskManagementService {

    private final TaskRepository taskRepo;

    public TaskManagementService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task findTasks(Long id) {
        return taskRepo.findTaskById(id);
    }

    public List<Task> findTasks(Long id,
                                TaskStatus taskStatus,
                                Integer year,
                                DayOfWeek dayOfWeek,
                                LocalDate date1,
                                LocalDate date2) {

        LocalDateTime startDateTime = (date1 != null) ? date1.atStartOfDay() : null;

        LocalDateTime endDateTime = (date2 != null) ? date2.plusDays(1).atStartOfDay() : null;

        return taskRepo.findTasks(id,
                taskStatus,
                year,
                dayOfWeek,
                startDateTime,
                endDateTime
        );
    }

    public void updateTask(Long id,
                           String title,
                           String body,
                           TaskStatus status,
                           LocalDateTime dueDate) {

        Task toUpdate = taskRepo.findTaskById(id);

        if(toUpdate != null){

            LocalDateTime lastUpdateDate = LocalDateTime.now();

            taskRepo.updateTask(
                    id,
                    (!Objects.equals(title, "")) ? title : toUpdate.getTitle(),
                    (status != null) ? status : toUpdate.getStatus(),
                    lastUpdateDate,
                    dueDate
            );

            taskRepo.updateBody(id,body);
            return;
        }

        throw  new ResourceNotFoundException("Theres no such entry in the database with Id: " + id);
    }

    public void create(Task task) {

        taskRepo.insertTask(
                task.getTitle(),
                task.getStatus(),
                task.getZoneOffset().toString(),
                task.getCreationDate(),
                task.getLastUpdateDate(),
                task.getDueDate()
        );

        taskRepo.insertTaskBody(task.getBody());
    }
}
