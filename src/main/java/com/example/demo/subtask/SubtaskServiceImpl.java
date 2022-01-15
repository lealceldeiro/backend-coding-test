package com.example.demo.subtask;

import com.example.demo.exception.SubtaskNotFoundException;
import com.example.demo.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return subtaskRepository.findAllWithParentTaskIdAndId(taskId, subtaskId)
                                .orElseThrow(() -> new SubtaskNotFoundException(taskId, subtaskId));
    }

    @Override
    public SubtaskDto createSubtask(int taskId, SubtaskDto subtaskDto) {
        var task = taskRepository.getOne(taskId);
        var subtaskEntity = subtaskTransformer.toEntity(subtaskDto);
        subtaskEntity.setParentTask(task);

        return subtaskTransformer.toIdDto(subtaskRepository.save(subtaskEntity));
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
