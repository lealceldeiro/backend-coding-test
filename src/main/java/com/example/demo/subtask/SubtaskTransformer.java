package com.example.demo.subtask;

import org.springframework.stereotype.Component;

@Component
public class SubtaskTransformer {
    public SubtaskDto toIdDto(SubtaskEntity entity) {
        var dto = new SubtaskDto();
        dto.setId(entity.getId());
        return dto;
    }

    public SubtaskDto toDto(SubtaskEntity entity) {
        return new SubtaskDto(entity.getId());
    }

    public SubtaskEntity toEntity(SubtaskDto dto) {
        return new SubtaskEntity();
    }

    public void updateEntity(SubtaskEntity entity, SubtaskDto dto) {
        // noop: nothing to update up to now
    }
}
