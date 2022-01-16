package com.example.demo.task;

import com.example.demo.TestsUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskPriorityTest {
    @Test
    void fromReturnsOKACorrectValue() {
        var values = TaskPriority.values();
        var randomValue = values[TestsUtil.RANDOM.nextInt(values.length)];

        assertEquals(randomValue, TaskPriority.from(randomValue.name()));
    }

    @Test
    void fromReturnsNullFromAnIncorrectValue() {
        assertNull(TaskPriority.from(UUID.randomUUID().toString()));
    }
}