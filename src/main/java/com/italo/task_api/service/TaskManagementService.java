package com.italo.task_api.service;

import com.italo.task_api.model.Task;
import com.italo.task_api.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TaskManagementService {

    private final TaskRepository taskRepo;

    public TaskManagementService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
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
