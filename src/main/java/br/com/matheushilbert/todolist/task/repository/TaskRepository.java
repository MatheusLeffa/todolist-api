package br.com.matheushilbert.todolist.task.repository;

import br.com.matheushilbert.todolist.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

    List<TaskModel> findByCreatorId(UUID userId);
}
