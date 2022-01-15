package com.example.demo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {
    private Integer id;
    @NotNull
    private String description;
    private Boolean completed;
    @NotNull
    private TaskPriority priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public TaskDto() {
    }

    public TaskDto(Integer id, String description, boolean completed, TaskPriority priority, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    // region getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    // endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskDto other = (TaskDto) o;
        return id.equals(other.getId())
               && completed == other.isCompleted()
               && description.equals(other.getDescription())
               && priority == other.getPriority();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, completed, priority);
    }

    @Override
    public String toString() {
        return "TaskDto{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", completed=" + completed +
               ", priority=" + priority +
               '}';
    }
}
