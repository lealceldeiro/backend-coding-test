package com.example.demo.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TaskEntity {
    @Id
    @GeneratedValue
    private int id;

    private String description;
    private boolean completed;
    private TaskPriority priority;

    private LocalDateTime createdAt;

    public TaskEntity(String description, boolean completed, TaskPriority priority, LocalDateTime createdAt) {
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdAt = createdAt;
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
