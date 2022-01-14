package com.example.demo.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public Page<TaskDto> getTasks(Pageable pageable) {
        return null;
    }

    @Override
    public TaskDto getTask(int taskId) {
        return null;
    }

    @Override
    public void createTask(TaskDto taskDto) {

    }

    @Override
    public void updateTask(TaskDto taskDto) {

    }

    @Override
    public void deleteTask(int taskId) {

    }
}
