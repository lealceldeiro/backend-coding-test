package com.example.demo.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskEntity> getTasks(Pageable pageable);
    TaskEntity getTask(int taskId);
    void createTask(TaskDto taskDto);
    void updateTask(int taskId, TaskDto taskDto);
    void deleteTask(int taskId);
}
