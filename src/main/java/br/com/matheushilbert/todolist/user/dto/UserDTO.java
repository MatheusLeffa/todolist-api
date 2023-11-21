package br.com.matheushilbert.todolist.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;

    @NotNull
    @Max(value = 30, message = "Login deve ter até 30 characteres.")
    private String login;

    @NotNull
    @Max(value = 50, message = "Login deve ter até 50 characteres.")
    private String name;

    private LocalDateTime createdAt;
}
