package com.example.demo.exception;

public class TaskNotFoundException extends NotFoundException {
    private static final long serialVersionUID = -2095142610889309816L;

    public TaskNotFoundException(int taskId) {
        super(String.format("Task with id %s not found", taskId));
    }
}
