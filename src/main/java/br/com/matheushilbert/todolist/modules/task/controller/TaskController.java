package br.com.matheushilbert.todolist.modules.task.controller;

import br.com.matheushilbert.todolist.Utils.Utils;
import br.com.matheushilbert.todolist.exception.NotAuthorizedException;
import br.com.matheushilbert.todolist.exception.NotFoundException;
import br.com.matheushilbert.todolist.modules.task.model.TaskModel;
import br.com.matheushilbert.todolist.modules.task.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        // Checks task Date(Now-Begin/End)
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.badRequest().body("The start and end date of the task must be after the current date.");
        }

        // Checks task Date(Begin-End)
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.badRequest().body("The task start date must be before the end date.");
        }
        // Set idUser from request
        UUID idUser = (UUID) request.getAttribute("idUser");
        taskModel.setIdUser(idUser);


        // Persists task
        TaskModel task = this.taskRepository.save(taskModel);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/")
    public List<TaskModel> listUserTasks(HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        return this.taskRepository.findByIdUser(idUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){

        TaskModel task = this.taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Task not found."));

        UUID idUser = (UUID) request.getAttribute("idUser");

        if (!task.getIdUser().equals(idUser)){
            throw new NotAuthorizedException("The user does not own this task.");
        }

        Utils.copyNonNullProperties(taskModel,task);
        TaskModel taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
