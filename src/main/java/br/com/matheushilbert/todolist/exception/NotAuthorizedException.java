package br.com.matheushilbert.todolist.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class NotAuthorizedException extends ResponseStatusException {

    public NotAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
        log.error(message);
    }

}
