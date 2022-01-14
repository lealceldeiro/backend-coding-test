package com.example.demo.task;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {
    private Integer id;
    @NotNull
    private String description;
    private boolean completed;
    @NotNull
    private TaskPriority priority;

    public TaskDto() {
    }

    public TaskDto(Integer id, String description, boolean completed, TaskPriority priority) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
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

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(final TaskPriority priority) {
        this.priority = priority;
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
        return completed == other.isCompleted()
               && description.equals(other.getDescription())
               && priority == other.getPriority();
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, completed, priority);
    }

    @Override
    public String toString() {
        return "TaskDto{" +
               "description='" + description + '\'' +
               ", completed=" + completed +
               ", priority=" + priority +
               '}';
    }
}
