package com.example.demo;

import com.example.demo.task.TaskDto;
import com.example.demo.task.TaskEntity;
import com.example.demo.task.TaskPriority;
import com.example.demo.task.TaskSearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.security.SecureRandom;
import java.time.LocalDateTime;
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

        var entity = new TaskEntity(UUID.randomUUID().toString(), RANDOM.nextBoolean(), priority, LocalDateTime.now());
        entity.setId(RANDOM.nextInt());

        return entity;
    }

    public static <T> Page<T> pageOf(T e) {
        return new PageImpl<>(List.of(e));
    }

    public static TaskDto taskDtoStub() {
        TaskPriority priority = randomPriority();

        return new TaskDto(RANDOM.nextInt(), UUID.randomUUID().toString(), RANDOM.nextBoolean(), priority, null);
    }

    public static TaskPriority randomPriority() {
        var values = TaskPriority.values();
        return values[RANDOM.nextInt(values.length)];
    }

    public static Pageable pageable() {
        return Pageable.unpaged();
    }

    public static TaskSearchSpecification searchSpec() {
        return new TaskSearchSpecification(null);
    }
}
