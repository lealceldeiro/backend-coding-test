package com.example.demo.subtask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubtaskServiceImpl implements SubtaskService {
    @Override
    @Transactional(readOnly = true)
    public Page<SubtaskDto> getSubtasks(final int taskId, final Pageable pageable) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public SubtaskDto getSubtask(final int taskId, final int subtaskId) {
        return null;
    }

    @Override
    public SubtaskDto createSubtask(final int taskId, final SubtaskDto subtaskDto) {
        return null;
    }

    @Override
    public void updateSubTask(final int taskId, final int subtaskId, final SubtaskDto subtaskDto) {

    }

    @Override
    public void deleteSubtask(final int taskId, final int subtaskId) {

    }
}
