package com.example.demo.task;

import com.example.demo.TestsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TaskTransformerTest {
    private TaskTransformer taskTransformer;

    @BeforeEach
    void setUp() {
        taskTransformer = new TaskTransformer();
    }

    @Test
    void toIdDto() {
        var entity = TestsUtil.taskEntityStub();
        var expectedId = entity.getId();

        var dto = taskTransformer.toIdDto(entity);
        assertEquals(expectedId, dto.getId());
    }

    @Test
    void toDto() {
        var entity = TestsUtil.taskEntityStub();

        var dto = taskTransformer.toDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getPriority(), dto.getPriority());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
        assertEquals(entity.isCompleted(), dto.getCompleted());
    }

    @Test
    void toEntity() {
        var dto = TestsUtil.taskDtoStub();

        var entity = taskTransformer.toEntity(dto);

        var defaultEntityIdWhenCreated = 0;
        assertEquals(defaultEntityIdWhenCreated, entity.getId());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getPriority(), entity.getPriority());
        assertNotNull(entity.getCreatedAt());
        assertEquals(dto.getCompleted(), entity.isCompleted());
    }

    @Test
    void updateEntity() {
        var entity = mock(TaskEntity.class);
        var dto = TestsUtil.taskDtoStub();

        taskTransformer.updateEntity(entity, dto);


        verify(entity, never()).setId(dto.getId());
        verify(entity, never()).setCreatedAt(any());
        verify(entity, times(1)).setDescription(dto.getDescription());
        verify(entity, times(1)).setPriority(dto.getPriority());
        verify(entity, times(1)).setPriority(dto.getPriority());
        verify(entity, times(1)).setCompleted(dto.getCompleted());
    }
}