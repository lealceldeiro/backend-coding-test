package com.example.demo.subtask;

import com.example.demo.TestsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubtaskTransformerTest {
    private SubtaskTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new SubtaskTransformer();
    }

    @Test
    void toIdDto() {
        var entity = TestsUtil.subtaskEntityStub();

        var dto = transformer.toIdDto(entity);

        assertEquals(entity.getId(), dto.getId());
    }

    @Test
    void toDto() {
        var entity = TestsUtil.subtaskEntityStub();

        var dto = transformer.toDto(entity);

        assertNotNull(dto); // no logic to assert for this method up to now: id equality is covered in other test
    }

    @Test
    void toEntity() {
        var dto = TestsUtil.subtaskDtoStub();

        var entity = transformer.toEntity(dto);

        assertNotNull(entity);  // no logic to assert for this method up to now: id equality is covered in other test
    }

    @Test
    void updateEntity() {
        // no logic to assert for this method up to now
    }
}