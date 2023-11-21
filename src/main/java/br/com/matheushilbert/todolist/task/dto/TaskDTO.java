package br.com.matheushilbert.todolist.task.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskDTO {

    private UUID id;

    @NotNull
    @Max(value = 50, message = "O titulo deve ter no máximo 50 caracteres!")
    private String title;

    private String description;

    private String priority = "low";

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;

    private LocalDateTime createdAt;

    private UUID creatorId;

}
