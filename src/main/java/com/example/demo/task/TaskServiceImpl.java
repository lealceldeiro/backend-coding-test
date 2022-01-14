package com.example.demo.task;

import com.example.demo.exception.TaskNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskTransformer taskTransformer;

    public TaskServiceImpl(TaskRepository taskRepository, TaskTransformer taskTransformer) {
        this.taskRepository = taskRepository;
        this.taskTransformer = taskTransformer;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskDto> getTasks(Pageable pageable) {
        return taskRepository.getAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto getTask(int taskId) {
        var taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return taskTransformer.toDto(taskEntity);
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        var taskEntity = taskTransformer.toEntity(taskDto);
        var savedEntity = taskRepository.save(taskEntity);

        return taskTransformer.toIdDto(savedEntity);
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        var taskEntity = taskRepository.findById(taskDto.getId())
                                       .orElseThrow(() -> new TaskNotFoundException(taskDto.getId()));
        taskTransformer.updateEntity(taskEntity, taskDto);
    }

    @Override
    public void deleteTask(int taskId) {
        if (taskRepository.deleteEntityWithId(taskId) < 1) {
            throw new TaskNotFoundException(1);
        }
    }
}
