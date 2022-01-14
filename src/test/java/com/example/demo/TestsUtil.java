package com.example.demo;

import com.example.demo.task.TaskDto;
import com.example.demo.task.TaskEntity;
import com.example.demo.task.TaskPriority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class TestsUtil {
    public static final Random RANDOM = new SecureRandom();

    private TestsUtil() {
    }

    public static TaskEntity taskEntityStub() {
        var values = TaskPriority.values();
        var priority = values[RANDOM.nextInt(values.length)];

        return new TaskEntity(RANDOM.nextInt(), UUID.randomUUID().toString(), RANDOM.nextBoolean(), priority);
    }

    public static <T> Page<T> pageOf(T e) {
        return new PageImpl<>(List.of(e));
    }

    public static TaskDto taskDtoStub() {
        var values = TaskPriority.values();
        var priority = values[RANDOM.nextInt(values.length)];

        return new TaskDto(UUID.randomUUID().toString(), RANDOM.nextBoolean(), priority);
    }
}