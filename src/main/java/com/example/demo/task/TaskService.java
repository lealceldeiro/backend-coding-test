package com.example.demo.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public interface TaskService {
    Page<TaskDto> getTasks(Pageable pageable, @Nullable Specification<TaskEntity> taskSearchSpecification);

    TaskDto getTask(int taskId);

    TaskDto createTask(TaskDto taskDto);

    void updateTask(int taskId, TaskDto taskDto);

    void deleteTask(int taskId);
}
