package com.example.demo.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskDto> getTasks(Pageable pageable);

    TaskDto getTask(int taskId);

    void createTask(TaskDto taskDto);

    void updateTask(TaskDto taskDto);

    void deleteTask(int taskId);
}
