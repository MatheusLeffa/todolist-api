package br.com.matheushilbert.todolist.controller;

import br.com.matheushilbert.todolist.Utils.Utils;
import br.com.matheushilbert.todolist.exception.NotAuthorizedException;
import br.com.matheushilbert.todolist.exception.NotFoundException;
import br.com.matheushilbert.todolist.model.TaskModel;
import br.com.matheushilbert.todolist.repository.TaskRepository;
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
            return ResponseEntity.badRequest().body("A data de inicio / data de termino da tarefa deve ser posterior a data atual.");
        }

        // Checks task Date(Begin-End)
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.badRequest().body("A data de inicio da tarefa deve ser anterior à data de termino.");
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
                .orElseThrow(()-> new NotFoundException("Tarefa não localizada."));

        UUID idUser = (UUID) request.getAttribute("idUser");

        if (!task.getIdUser().equals(idUser)){
            throw new NotAuthorizedException("O usuário não é dono desta tarefa.");
        }

        Utils.copyNonNullProperties(taskModel,task);
        TaskModel taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
