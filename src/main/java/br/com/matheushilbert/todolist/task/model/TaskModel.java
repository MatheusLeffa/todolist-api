package br.com.matheushilbert.todolist.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String description;

    @Column(length = 50, nullable = false)
    private String title;

    private String priority = "Low";

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private UUID idUser;

    public void setTitle(String title) {
        if (title.length() > 50) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O titulo deve ter no máximo 50 caracteres.");
        }
        this.title = title;
    }
}
