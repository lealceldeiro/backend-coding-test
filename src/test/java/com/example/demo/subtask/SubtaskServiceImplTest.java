package com.example.demo.subtask;

import com.example.demo.TestsUtil;
import com.example.demo.exception.SubtaskNotFoundException;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.task.TaskRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {SubtaskService.class, SubtaskServiceImpl.class})
class SubtaskServiceImplTest {
    @MockBean
    private SubtaskRepository subtaskRepository;
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private SubtaskTransformer subtaskTransformer;

    @Autowired
    private SubtaskService subtaskService;

    @Test
    void getSubtasks() {
        var taskId = TestsUtil.RANDOM.nextInt();
        var expected = TestsUtil.pageOf(TestsUtil.subtaskDtoStub());
        when(subtaskRepository.findAllWithParentTaskId(eq(taskId), any(Pageable.class))).thenReturn(expected);

        var actualSubtasks = subtaskService.getSubtasks(taskId, TestsUtil.pageable());

        assertEquals(expected, actualSubtasks);
    }

    @Test
    void getSubtaskReturnsOk() {
        var expected = TestsUtil.subtaskDtoStub();
        when(subtaskRepository.findOneWithParentTaskIdAndId(anyInt(), anyInt())).thenReturn(Optional.of(expected));

        var actual = subtaskService.getSubtask(TestsUtil.RANDOM.nextInt(), TestsUtil.RANDOM.nextInt());

        assertEquals(expected, actual);
    }

    @Test
    void getSubtaskThrowsNotFoundExceptionIfSubtaskDoesntExist() {
        when(subtaskRepository.findOneWithParentTaskIdAndId(anyInt(), anyInt())).thenReturn(Optional.empty());

        var randomId = TestsUtil.RANDOM.nextInt();
        assertThrows(SubtaskNotFoundException.class, () -> subtaskService.getSubtask(randomId, randomId));
    }

    @Test
    void createSubtask() {
        var taskId = TestsUtil.RANDOM.nextInt();
        var subtaskDto = TestsUtil.subtaskDtoStub();
        var subtaskEntity = TestsUtil.subtaskEntityStub();
        var expected = TestsUtil.subtaskDtoStub();

        when(taskRepository.getById(taskId)).thenReturn(TestsUtil.taskEntityStub());
        when(subtaskTransformer.toEntity(subtaskDto)).thenReturn(subtaskEntity);
        when(subtaskRepository.saveAndFlush(subtaskEntity)).thenReturn(subtaskEntity);
        when(subtaskTransformer.toIdDto(subtaskEntity)).thenReturn(expected);

        var actual = subtaskService.createSubtask(taskId, subtaskDto);

        assertEquals(expected, actual);
        verify(taskRepository, times(1)).getById(taskId);
        verify(subtaskRepository, times(1)).saveAndFlush(subtaskEntity);
        verify(subtaskTransformer, times(1)).toEntity(subtaskDto);
        verify(subtaskTransformer, times(1)).toIdDto(subtaskEntity);
    }

    @Test
    void createSubtaskThrowsNotFoundExceptionIfTheresAnExceptionWhileSavingTheData() {
        when(subtaskTransformer.toEntity(any())).thenReturn(TestsUtil.subtaskEntityStub());
        when(subtaskRepository.saveAndFlush(any()))
                .thenThrow(new RuntimeException("Some exception propagated from lower layers"));

        var dto = new SubtaskDto();
        assertThrows(TaskNotFoundException.class, () -> subtaskService.createSubtask(1, dto));
    }

    @Test
    void updateSubTaskUpdatesTheTaskOK() {
        var randomId = TestsUtil.RANDOM.nextInt();
        var dto = TestsUtil.subtaskDtoStub();
        var entity = TestsUtil.subtaskEntityStub();
        when(subtaskRepository.findWithParentTaskIdAndId(anyInt(), anyInt())).thenReturn(Optional.of(entity));

        subtaskService.updateSubTask(randomId, randomId, dto);

        verify(subtaskTransformer, times(1)).updateEntity(entity, dto);
    }

    @Test
    void updateSubTaskThrowsNotFoundExceptionIfSubtaskDoesntExist() {
        var randomId = TestsUtil.RANDOM.nextInt();
        var dto = TestsUtil.subtaskDtoStub();
        when(subtaskRepository.findWithParentTaskIdAndId(anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThrows(SubtaskNotFoundException.class, () -> subtaskService.updateSubTask(randomId, randomId, dto));
    }

    @Test
    void deleteSubtaskOK() {
        var taskId = TestsUtil.RANDOM.nextInt();
        var subtaskId = TestsUtil.RANDOM.nextInt();
        when(subtaskRepository.deleteEntityWithParentTaskIdAndId(taskId, subtaskId)).thenReturn(1);

        subtaskService.deleteSubtask(taskId, subtaskId);

        verify(subtaskRepository, times(1)).deleteEntityWithParentTaskIdAndId(taskId, subtaskId);
    }

    @Test
    void deleteSubtask() {
        when(subtaskRepository.deleteEntityWithParentTaskIdAndId(anyInt(), anyInt())).thenReturn(0);

        var randomId = TestsUtil.RANDOM.nextInt();
        assertThrows(SubtaskNotFoundException.class, () -> subtaskService.deleteSubtask(randomId, randomId));
    }
}