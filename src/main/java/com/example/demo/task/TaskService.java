package com.example.demo.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskDto> getTasks(Pageable pageable, TaskSearchSpecification taskSearchSpecification);

    TaskDto getTask(int taskId);

    TaskDto createTask(TaskDto taskDto);

    void updateTask(int taskId, TaskDto taskDto);

    void deleteTask(int taskId);
}
