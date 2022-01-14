package com.example.demo.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    @Query("select new com.example.demo.task.TaskDto(task.id, task.description, task.completed, task.priority) "
           + "from TaskEntity task")
    Page<TaskDto> getAll(Pageable pageable);

    @Modifying
    @Query("delete from TaskEntity t where t.id = :id")
    int deleteEntityWithId(int id);
}
