package com.example.demo.subtask;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubtaskDto {
    private Integer id;

    public SubtaskDto() {
    }

    public SubtaskDto(Integer id, String description) {
        this.id = id;
    }

    // region getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        SubtaskDto other = (SubtaskDto) o;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SubtaskDto{id=" + id + '}';
    }
}
