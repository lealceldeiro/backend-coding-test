package com.example.demo.subtask;

import com.example.demo.TestsUtil;
import com.example.demo.exception.AppExceptionHandler;
import com.example.demo.exception.SubtaskNotFoundException;
import com.example.demo.exception.TaskNotFoundException;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@AutoConfigureMockMvc
@EnableSpringDataWebSupport
@SpringBootTest(classes = {SubtaskController.class, AppExceptionHandler.class})
class SubtaskControllerTest {
    private static final int TASK_ID = Math.abs(TestsUtil.RANDOM.nextInt());
    private static final String SUB_TASK_URL = String.format("/task/%s/subtask", TASK_ID);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @MockBean
    private SubtaskService subtaskService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSubtasks() throws Exception {
        var subtaskStub = TestsUtil.subtaskDtoStub();
        when(subtaskService.getSubtasks(eq(TASK_ID), any(Pageable.class))).thenReturn(TestsUtil.pageOf(subtaskStub));

        mockMvc.perform(get(SUB_TASK_URL))
               .andExpect(status().isOk())
               .andExpect(jsonPath("content[*].id").value(subtaskStub.getId()));

        verify(subtaskService, times(1)).getSubtasks(eq(TASK_ID), any(Pageable.class));
    }

    @Test
    void getSubtask() throws Exception {
        var subtaskStub = TestsUtil.subtaskDtoStub();
        when(subtaskService.getSubtask(TASK_ID, subtaskStub.getId())).thenReturn(subtaskStub);

        mockMvc.perform(get(SUB_TASK_URL + "/{subtaskId}", subtaskStub.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id").value(subtaskStub.getId()));
    }

    @Test
    void getSubtaskThrowsNotFoundIfTheresNoEntityWithGivenId() throws Exception {
        var notFoundId = -1;
        when(subtaskService.getSubtask(TASK_ID, notFoundId))
                .thenThrow(new SubtaskNotFoundException(TASK_ID, notFoundId));

        var notFoundMsg = "Subtask with id " + notFoundId + " and parent task id " + TASK_ID + " not found";
        mockMvc.perform(get(SUB_TASK_URL + "/{subtaskId}", notFoundId))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("message").value(notFoundMsg));
    }

    @Test
    void createSubtask() throws Exception {
        var dtoStub = TestsUtil.subtaskDtoStub();
        var expected = new SubtaskDto();
        expected.setId(dtoStub.getId());

        when(subtaskService.createSubtask(TASK_ID, dtoStub)).thenReturn(expected);

        mockMvc.perform(post(SUB_TASK_URL).contentType(MediaType.APPLICATION_JSON)
                                          .characterEncoding(StandardCharsets.UTF_8.toString())
                                          .content(MAPPER.writeValueAsBytes(dtoStub)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id").value(expected.getId()));

        verify(subtaskService, times(1)).createSubtask(TASK_ID, dtoStub);
    }

    @Test
    void createSubtaskThrowsNotFoundIfThereNoTaskWithTheGivenId() throws Exception {
        when(subtaskService.createSubtask(eq(TASK_ID), any(SubtaskDto.class)))
                .thenThrow(new TaskNotFoundException(TASK_ID));

        mockMvc.perform(post(SUB_TASK_URL).contentType(MediaType.APPLICATION_JSON)
                                          .characterEncoding(StandardCharsets.UTF_8.toString())
                                          .content(MAPPER.writeValueAsBytes(new SubtaskDto())))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("message").value("Task with id " + TASK_ID + " not found"));
    }

    @Test
    void updateSubtask() throws Exception {
        var dtoStub = TestsUtil.subtaskDtoStub();

        mockMvc.perform(put(SUB_TASK_URL + "/{subtaskId}", dtoStub.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8.toString())
                                .content(MAPPER.writeValueAsBytes(dtoStub)))
               .andExpect(status().isNoContent());

        verify(subtaskService, times(1)).updateSubTask(TASK_ID, dtoStub.getId(), dtoStub);
    }

    @Test
    void updateSubtaskThrowsNotFoundIfTheresNoTaskWithTheGivenId() throws Exception {
        doThrow(new TaskNotFoundException(TASK_ID))
                .when(subtaskService).updateSubTask(eq(TASK_ID), anyInt(), any(SubtaskDto.class));

        mockMvc.perform(put(SUB_TASK_URL + "/{subtaskId}", TestsUtil.RANDOM.nextInt())
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8.toString())
                                .content(MAPPER.writeValueAsBytes(new SubtaskDto())))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("message").value("Task with id " + TASK_ID + " not found"));
    }

    @Test
    void deleteSubtask() throws Exception {
        var subtaskId = TestsUtil.RANDOM.nextInt();

        mockMvc.perform(delete(SUB_TASK_URL + "/{subtaskId}", subtaskId)).andExpect(status().isNoContent());

        verify(subtaskService, times(1)).deleteSubtask(TASK_ID, subtaskId);
    }

    @Test
    void deleteSubtaskThrowsNotFoundIfTheresNoEntityWithGivenId() throws Exception {
        var notFoundId = -1;
        doThrow(new SubtaskNotFoundException(TASK_ID, notFoundId))
                .when(subtaskService).deleteSubtask(TASK_ID, notFoundId);

        var notFoundMsg = "Subtask with id " + notFoundId + " and parent task id " + TASK_ID + " not found";
        mockMvc.perform(delete(SUB_TASK_URL + "/{subtaskId}", notFoundId))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("message").value(notFoundMsg));
    }
}