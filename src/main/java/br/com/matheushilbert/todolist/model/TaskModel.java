package br.com.matheushilbert.todolist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @Max(value = 50, message = "O titulo deve ter no máximo 50 caracteres!")
    @NotNull(message = "O titulo não pode ser Null")
    @Column(length = 50, nullable = false)
    private String title;

    private String priority = "Low";
    @NotNull(message = "A data de inicio não pode ser Null")
    @Column(nullable = false)
    private LocalDateTime startAt;

    @NotNull(message = "A data de fim não pode ser Null")
    @Column(nullable = false)
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private UUID idUser;

}
