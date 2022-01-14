package com.example.demo.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public Page<TaskEntity> getTasks(Pageable pageable) {
        return null;
    }

    @Override
    public TaskEntity getTask(int taskId) {
        return null;
    }

    @Override
    public void createTask(TaskDto taskDto) {

    }

    @Override
    public void updateTask(int taskId, final TaskDto taskDto) {

    }

    @Override
    public void deleteTask(int taskId) {

    }
}
