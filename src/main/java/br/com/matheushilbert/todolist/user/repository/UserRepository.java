package br.com.matheushilbert.todolist.user.repository;

import br.com.matheushilbert.todolist.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByLogin(String login);
}
