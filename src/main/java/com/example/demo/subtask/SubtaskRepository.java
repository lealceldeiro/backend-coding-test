package com.example.demo.subtask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubtaskRepository extends JpaRepository<SubtaskEntity, Integer> {
    @Query(value = "select new com.example.demo.subtask.SubtaskDto(st.id) "
                   + "from SubtaskEntity st "
                   + "join st.parentTask "
                   + "where st.parentTask.id = :parentTaskId",
           countQuery = "select count(st.id) "
                        + "from SubtaskEntity st "
                        + "join st.parentTask "
                        + "where st.parentTask.id = :parentTaskId")
    Page<SubtaskDto> findAllWithParentTaskId(int parentTaskId, Pageable pageable);

    @Query("select new com.example.demo.subtask.SubtaskDto(st.id) "
           + "from SubtaskEntity st "
           + "join st.parentTask "
           + "where st.parentTask.id = :parentTaskId and st.id = :subtaskId")
    Optional<SubtaskDto> findOneWithParentTaskIdAndId(int parentTaskId, int subtaskId);

    @Query("select st "
           + "from SubtaskEntity st "
           + "join st.parentTask "
           + "where st.parentTask.id = :parentTaskId and st.id = :subtaskId")
    Optional<SubtaskEntity> findWithParentTaskIdAndId(int parentTaskId, int subtaskId);

    @Modifying
    @Query("delete from SubtaskEntity st where st.parentTask.id = :parentTaskId and st.id = :subtaskId")
    int deleteEntityWithParentTaskIdAndId(int parentTaskId, int subtaskId);

    @Modifying
    @Query("delete from SubtaskEntity st where st.parentTask.id = :parentTaskId")
    int deleteEntityWithParentTaskId(int parentTaskId);
}
