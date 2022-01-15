package com.example.demo.subtask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task/{taskId}/subtask")
public class SubtaskController {
    private final SubtaskService subtaskService;

    private SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @GetMapping
    public Page<SubtaskDto> getSubtasks(@PathVariable int taskId, Pageable pageable) {
        return subtaskService.getSubtasks(taskId, pageable);
    }

    @GetMapping("/{subtaskId}")
    public SubtaskDto getSubtask(@PathVariable int taskId, @PathVariable int subtaskId) {
        return subtaskService.getSubtask(taskId, subtaskId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubtaskDto createSubtask(@PathVariable int taskId, @Validated @RequestBody SubtaskDto subtaskDto) {
        return subtaskService.createSubtask(taskId, subtaskDto);
    }

    @PutMapping("/{subtaskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSubtask(@PathVariable int taskId, @PathVariable int subtaskId,
                              @Validated @RequestBody SubtaskDto subtaskDto) {
        subtaskService.updateSubTask(taskId, subtaskId, subtaskDto);
    }

    @DeleteMapping("/{subtaskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubtask(@PathVariable int taskId, @PathVariable int subtaskId) {
        subtaskService.deleteSubtask(taskId, subtaskId);
    }
}
