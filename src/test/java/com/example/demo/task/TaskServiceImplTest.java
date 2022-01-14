package com.example.demo.task;

import com.example.demo.TestsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {TaskService.class, TaskServiceImpl.class})
class TaskServiceImplTest {
    @Autowired
    private TaskService taskService;

    @Test
    void getTasks() {
        var actualTasks = taskService.getTasks(TestsUtil.pageable());

        assertNotNull(actualTasks);
    }

    @Test
    void getTask() {
        var taskEntityStub = TestsUtil.taskEntityStub();

        var actualTask = taskService.getTask(taskEntityStub.getId());

        assertNotNull(actualTask);
    }

    @Test
    void createTask() {
        var taskDto = TestsUtil.taskDtoStub();
        taskService.createTask(taskDto);
    }

    @Test
    void updateTask() {
        var taskEntity = TestsUtil.taskEntityStub();
        var taskDto = TestsUtil.taskDtoStub();

        taskService.updateTask(taskEntity.getId(), taskDto);
    }

    @Test
    void deleteTask() {
        var taskEntity = TestsUtil.taskEntityStub();

        taskService.deleteTask(taskEntity.getId());
    }
}