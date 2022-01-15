package com.example.demo.task;

public enum TaskPriority {
    LOW,
    MEDIUM,
    HIGH;

    public static TaskPriority from(String rawValue) {
        try {
            return valueOf(rawValue);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
