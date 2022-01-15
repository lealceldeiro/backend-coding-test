package com.example.demo.task;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TaskEntity {

    @Id
    @GeneratedValue
    private int id;

    private String description;
    private boolean completed;
    private TaskPriority priority;

    private LocalDateTime createdAt;

    public TaskEntity() {
    }

    public TaskEntity(String description, boolean completed, TaskPriority priority, LocalDateTime createdAt) {
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
