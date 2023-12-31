package br.com.matheushilbert.todolist.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AlreadyExistsException extends ResponseStatusException {

    public AlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        log.error(message);
    }
}
