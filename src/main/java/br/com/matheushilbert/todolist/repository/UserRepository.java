package br.com.matheushilbert.todolist.repository;

import br.com.matheushilbert.todolist.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    public UserModel findByLogin(String login);
}
