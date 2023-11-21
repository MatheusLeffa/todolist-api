package br.com.matheushilbert.todolist.task.service;

import br.com.matheushilbert.todolist.Utils.Utils;
import br.com.matheushilbert.todolist.exception.NotAuthorizedException;
import br.com.matheushilbert.todolist.exception.NotFoundException;
import br.com.matheushilbert.todolist.task.model.TaskModel;
import br.com.matheushilbert.todolist.task.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskModel create(TaskModel task) {
        validateTaskDate(task);
        return taskRepository.save(task);
    }

    private void validateTaskDate(TaskModel task) {
        LocalDateTime currentDate = LocalDateTime.now();
        boolean isTaskDateValid = currentDate.isBefore(task.getStartAt())
                || currentDate.isBefore(task.getEndAt())
                || task.getStartAt().isBefore(task.getEndAt());


        if (!isTaskDateValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task date is invalid.");
        }
    }

    public TaskModel update(TaskModel task) {
        return taskRepository.save(task);
    }

    public TaskModel findById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));
    }

    public List<TaskModel> findByCreatorId(UUID creatorId) {
        return taskRepository.findByCreatorId(creatorId);
    }

    public Boolean isTaskFromUser(UUID creatorId, UUID userId) {
        return creatorId.equals(userId);
    }

}



