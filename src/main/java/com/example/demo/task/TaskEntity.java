package com.example.demo.task;

import com.example.demo.subtaskentity.SubtaskEntity;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class TaskEntity {

    @Id
    @GeneratedValue
    private int id;

    private String description;
    private boolean completed;
    private TaskPriority priority;

    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<SubtaskEntity> subtasks;

    public TaskEntity() {
        this.subtasks = new HashSet<>();
    }

    public TaskEntity(String description, boolean completed, TaskPriority priority, LocalDateTime createdAt) {
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdAt = createdAt;
        this.subtasks = new HashSet<>();
    }

    // region
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
    // endregion

    public void addSubtask(SubtaskEntity subtaskEntity) {
        subtasks.add(subtaskEntity);
    }

    public void deleteSubtask(SubtaskEntity subtaskEntity) {
        subtasks.remove(subtaskEntity);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) {
            return false;
        }
        TaskEntity entity = (TaskEntity) other;
        return Objects.equals(id, entity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "TaskEntity{id=" + id + '}';
    }
}
