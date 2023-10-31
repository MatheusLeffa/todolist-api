package br.com.matheushilbert.todolist.modules.task.repository;

import br.com.matheushilbert.todolist.modules.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

    public List<TaskModel> findByIdUser(UUID idUser);
}
