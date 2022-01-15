package com.example.demo.subtask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubtaskService {
    Page<SubtaskDto> getSubtasks(int taskId, Pageable pageable);

    SubtaskDto getSubtask(int taskId, int subtaskId);

    SubtaskDto createSubtask(int taskId, SubtaskDto subtaskDto);

    void updateSubTask(int taskId, int subtaskId, SubtaskDto subtaskDto);

    void deleteSubtask(int taskId, int subtaskId);
}
