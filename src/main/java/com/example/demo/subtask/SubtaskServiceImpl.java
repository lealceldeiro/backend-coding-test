package com.example.demo.subtask;

import com.example.demo.exception.SubtaskNotFoundException;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubtaskServiceImpl implements SubtaskService {
    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;
    private final SubtaskTransformer subtaskTransformer;

    @Override
    @Transactional(readOnly = true)
    public Page<SubtaskDto> getSubtasks(int taskId, Pageable pageable) {
        return subtaskRepository.findAllWithParentTaskId(taskId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public SubtaskDto getSubtask(int taskId, int subtaskId) {
        return subtaskRepository.findOneWithParentTaskIdAndId(taskId, subtaskId)
                                .orElseThrow(() -> new SubtaskNotFoundException(taskId, subtaskId));
    }

    @Override
    public SubtaskDto createSubtask(int taskId, SubtaskDto subtaskDto) {
        var task = taskRepository.getById(taskId);
        var subtaskEntity = subtaskTransformer.toEntity(subtaskDto);
        subtaskEntity.setParentTask(task);

        SubtaskEntity savedSubtask;
        try {
            savedSubtask = subtaskRepository.saveAndFlush(subtaskEntity); // flush to detect any FK constraint violation
        } catch (Exception e) {
            log.error("Error while creating subtask for task with id {}", taskId, e);
            var errorMsg = "Error creating subtask. Verify the task exist and the subtask data is correct";
            throw new TaskNotFoundException(errorMsg);
        }
        return subtaskTransformer.toIdDto(savedSubtask);
    }

    @Override
    public void updateSubTask(int taskId, int subtaskId, SubtaskDto subtaskDto) {
        var subtaskEntity = subtaskRepository.findWithParentTaskIdAndId(taskId, subtaskId)
                                             .orElseThrow(() -> new SubtaskNotFoundException(taskId, subtaskId));
        subtaskTransformer.updateEntity(subtaskEntity, subtaskDto);
    }

    @Override
    public void deleteSubtask(int taskId, int subtaskId) {
        if (subtaskRepository.deleteEntityWithParentTaskIdAndId(taskId, subtaskId) < 1) {
            throw new SubtaskNotFoundException(taskId, subtaskId);
        }
    }
}
