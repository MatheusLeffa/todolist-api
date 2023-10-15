package br.com.matheushilbert.todolist.task.controller;

import br.com.matheushilbert.todolist.Utils.Utils;
import br.com.matheushilbert.todolist.task.model.TaskModel;
import br.com.matheushilbert.todolist.task.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        // Checks task Date
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio / data de termino da tarefa deve ser posterior a data atual.");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio da tarefa deve ser anterior à data de termino.");
        }
        // Set idUser from request
        UUID idUser = (UUID) request.getAttribute("idUser");
        taskModel.setIdUser(idUser);


        // Persists task
        TaskModel task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> listUserTasks(HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        return this.taskRepository.findByIdUser(idUser);
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){

        var task = this.taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe task com esse ID."));

        Utils.copyNonNullProperties(taskModel,task);

        return this.taskRepository.save(task);
    }
}
