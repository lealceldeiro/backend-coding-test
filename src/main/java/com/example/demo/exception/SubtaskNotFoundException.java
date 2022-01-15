package com.example.demo.exception;

public class SubtaskNotFoundException extends NotFoundException {
    private static final long serialVersionUID = 7575743080388529446L;

    public SubtaskNotFoundException(int taskId, int subtaskId) {
        super(String.format("Subtask with id %s and parent task id %s not found", subtaskId, taskId));
    }
}
