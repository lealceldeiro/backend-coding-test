package com.example.demo.task;

import com.example.demo.TestsUtil;
import com.example.demo.exception.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TaskService.class, TaskServiceImpl.class})
class TaskServiceImplTest {
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private TaskTransformer taskTransformer;

    @Autowired
    private TaskService taskService;

    @Test
    void getTasks() {
        var expected = TestsUtil.pageOf(TestsUtil.taskDtoStub());
        when(taskRepository.getAll(any(Pageable.class))).thenReturn(expected);

        var actualTasks = taskService.getTasks(TestsUtil.pageable());

        assertEquals(expected, actualTasks);
    }

    @Test
    void getTask() {
        var entity = TestsUtil.taskEntityStub();

        var expected = new TaskDto(entity.getId(), entity.getDescription(), entity.isCompleted(), entity.getPriority());

        when(taskRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(taskTransformer.toDto(entity)).thenCallRealMethod();

        var actualTask = taskService.getTask(entity.getId());

        assertEquals(expected, actualTask);
    }

    @Test
    void createTask() {
        var taskDto = TestsUtil.taskDtoStub();
        var entity = new TaskEntity(taskDto.getDescription(), taskDto.isCompleted(), taskDto.getPriority());
        entity.setId(TestsUtil.RANDOM.nextInt());

        var expected = new TaskDto();
        expected.setId(entity.getId());

        when(taskTransformer.toEntity(taskDto)).thenReturn(entity);
        when(taskRepository.save(entity)).thenReturn(entity);
        when(taskTransformer.toIdDto(entity)).thenReturn(expected);

        var actualIdDto = taskService.createTask(taskDto);

        assertEquals(expected, actualIdDto);
        verify(taskRepository, times(1)).save(entity);
    }

    @Test
    void updateTask() {
        var taskDto = TestsUtil.taskDtoStub();
        var entity = TestsUtil.taskEntityStub();
        entity.setId(taskDto.getId());

        when(taskRepository.findById(taskDto.getId())).thenReturn(Optional.of(entity));

        taskService.updateTask(taskDto);

        verify(taskTransformer, times(1)).updateEntity(entity, taskDto);
    }

    @Test
    void updateTaskThrowsNotFoundIfRepositoryDoesntFindEntity() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.empty());

        var stub = TestsUtil.taskDtoStub();
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(stub));
    }

    @Test
    void deleteTask() {
        var taskId = TestsUtil.RANDOM.nextInt();
        when(taskRepository.deleteEntityWithId(taskId)).thenReturn(1);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteEntityWithId(taskId);
    }

    @Test
    void deleteTaskThrowsNotFoundIfRepositoryDoesntFindEntity() {
        var taskId = TestsUtil.RANDOM.nextInt();
        when(taskRepository.deleteEntityWithId(taskId)).thenReturn(0);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
    }
}