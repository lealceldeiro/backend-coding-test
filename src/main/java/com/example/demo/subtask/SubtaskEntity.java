package com.example.demo.subtask;

import com.example.demo.task.TaskEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

// It's not clear whether a subtask is actually a task (maybe with some more attributes), so I created a brand-new class
// as stated in the README.md file. If a subtask is going to hold the same attributes that a task holds, then a
// composite structure could have been created using the same TaskEntity
@Setter
@Getter
@NoArgsConstructor
@Entity
public class SubtaskEntity {
    @Id
    @GeneratedValue
    private int id;

    public SubtaskEntity(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TaskEntity parentTask;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) {
            return false;
        }
        SubtaskEntity entity = (SubtaskEntity) other;
        return Objects.equals(id, entity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "SubtaskEntity{id=" + id + '}';
    }
}
