package com.example.demo.task;

import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.subtask.SubtaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final TaskTransformer taskTransformer;

    @Override
    @Transactional(readOnly = true)
    public Page<TaskDto> getTasks(Pageable pageable, @Nullable Specification<TaskEntity> taskSearchSpecification) {
        var page = taskRepository.findAll(taskSearchSpecification, pageable);
        var pageDtoContent = page.getContent().stream().map(taskTransformer::toDto).collect(Collectors.toList());
        return new PageImpl<>(pageDtoContent, pageable, page.getTotalElements());
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
    public void updateTask(int taskId, TaskDto taskDto) {
        var taskEntity = taskRepository.findById(taskId)
                                       .orElseThrow(() -> new TaskNotFoundException(taskId));
        taskTransformer.updateEntity(taskEntity, taskDto);
    }

    @Override
    public void deleteTask(int taskId) {
        var subtaskDeleted = subtaskRepository.deleteEntityWithParentTaskId(taskId);
        if (taskRepository.deleteEntityWithId(taskId) < 1) {
            if (subtaskDeleted > 0) {
                log.warn("Possible inconsistency found: {} subtasks with parent task id {} were deleted. "
                         + "But no parent task with that id was deleted", subtaskDeleted, taskId);
            }
            throw new TaskNotFoundException(taskId);
        }
    }
}
