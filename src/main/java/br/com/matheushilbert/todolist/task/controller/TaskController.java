package br.com.matheushilbert.todolist.task.controller;

import br.com.matheushilbert.todolist.Utils.Utils;
import br.com.matheushilbert.todolist.task.dto.TaskDTO;
import br.com.matheushilbert.todolist.task.mapper.TaskMapper;
import br.com.matheushilbert.todolist.task.model.TaskModel;
import br.com.matheushilbert.todolist.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    public final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody @Valid TaskDTO task) {

        TaskModel taskModel = TaskMapper.dtoToEntity(task);

        TaskDTO taskDTO = TaskMapper.entityToDto(taskService.create(taskModel));
        return ResponseEntity.ok().body(taskDTO);
    }

    @GetMapping("/{id}")
    public List<TaskDTO> listUserTasks(@PathVariable("id") UUID userId) {
        return taskService.findByCreatorId(userId)
                .stream()
                .map(TaskMapper::entityToDto)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id) {
        TaskModel task = taskService.findById(id);

        Utils.copyNonNullProperties(taskModel, task);
        TaskDTO taskDTO = TaskMapper.entityToDto(taskService.update(task));

        return ResponseEntity.ok().body(taskDTO);
    }
}
