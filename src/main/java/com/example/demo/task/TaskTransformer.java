package com.example.demo.task;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class TaskTransformer {
    public TaskDto toIdDto(TaskEntity entity) {
        var dto = new TaskDto();
        dto.setId(entity.getId());
        return dto;
    }

    public TaskDto toDto(TaskEntity entity) {
        return new TaskDto(entity.getId(), entity.getDescription(), entity.isCompleted(), entity.getPriority(),
                           entity.getCreatedAt());
    }

    public TaskEntity toEntity(TaskDto dto) {
        boolean completed = Optional.ofNullable(dto.isCompleted()).orElse(Boolean.FALSE);
        // probably some configuration to allow flexibility in the timezone to use here would be nice
        var createdAt = LocalDateTime.now(ZoneId.of("UTC"));

        return new TaskEntity(dto.getDescription(), completed, dto.getPriority(), createdAt);
    }

    public void updateEntity(TaskEntity entity, TaskDto dto) {
        boolean completed = Optional.ofNullable(dto.isCompleted()).orElse(Boolean.FALSE);
        entity.setDescription(dto.getDescription());
        entity.setCompleted(completed);
        entity.setPriority(dto.getPriority());
    }
}
