package com.example.demo.task;

import com.example.demo.TestsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@AutoConfigureMockMvc
@EnableSpringDataWebSupport
@SpringBootTest(classes = TaskController.class)
class TaskControllerTest {
    private static final String TASK_URL = "/task";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTasks() throws Exception {
        var taskStub = TestsUtil.taskEntityStub();
        when(taskService.getTasks(any(Pageable.class))).thenReturn(TestsUtil.pageOf(taskStub));

        mockMvc.perform(MockMvcRequestBuilders.get(TASK_URL))
               .andExpect(status().isOk())
               .andExpect(jsonPath("content[*].id").value(taskStub.getId()))
               .andExpect(jsonPath("content[*].description").value(taskStub.getDescription()))
               .andExpect(jsonPath("content[*].completed").value(taskStub.isCompleted()))
               .andExpect(jsonPath("content[*].priority").value(taskStub.getPriority().toString()));

        verify(taskService, times(1)).getTasks(any(Pageable.class));
    }

    @Test
    void getTask() throws Exception {
        var taskStub = TestsUtil.taskEntityStub();
        when(taskService.getTask(taskStub.getId())).thenReturn(taskStub);

        mockMvc.perform(MockMvcRequestBuilders.get(TASK_URL + "/{taskId}", taskStub.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id").value(taskStub.getId()))
               .andExpect(jsonPath("description").value(taskStub.getDescription()))
               .andExpect(jsonPath("completed").value(taskStub.isCompleted()))
               .andExpect(jsonPath("priority").value(taskStub.getPriority().toString()));
    }

    @Test
    void createTask() throws Exception {
        var dtoStub = TestsUtil.taskDtoStub();

        mockMvc.perform(post(TASK_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8.toString())
                                .content(MAPPER.writeValueAsBytes(dtoStub)))
               .andExpect(status().isCreated());

        verify(taskService, times(1)).createTask(dtoStub);
    }

    @Test
    void createTaskThrowsBadRequestIfInvalidInput() throws Exception {
        mockMvc.perform(post(TASK_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8.toString())
                                .content(MAPPER.writeValueAsBytes(new TaskDto())))
               .andExpect(status().isBadRequest());

        verify(taskService, never()).createTask(any());
    }

    @Test
    void updateTask() throws Exception {
        var dtoStub = TestsUtil.taskDtoStub();
        var taskId = TestsUtil.RANDOM.nextInt();

        mockMvc.perform(put(TASK_URL + "/{taskId}", taskId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8.toString())
                                .content(MAPPER.writeValueAsBytes(dtoStub)))
               .andExpect(status().isNoContent());

        verify(taskService, times(1)).updateTask(taskId, dtoStub);
    }

    @Test
    void updateTaskThrowsBadRequestIfInvalidInput() throws Exception {
        mockMvc.perform(put(TASK_URL + "/{taskId}", TestsUtil.RANDOM.nextInt())
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8.toString())
                                .content(MAPPER.writeValueAsBytes(new TaskDto())))
               .andExpect(status().isBadRequest());

        verify(taskService, never()).updateTask(anyInt(), any());
    }

    @Test
    void deleteTask() throws Exception {
        var taskId = TestsUtil.RANDOM.nextInt();

        mockMvc.perform(delete(TASK_URL + "/{taskId}", taskId)).andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(taskId);
    }
}