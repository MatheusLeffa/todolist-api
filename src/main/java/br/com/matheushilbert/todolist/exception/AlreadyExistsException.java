package br.com.matheushilbert.todolist.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AlreadyExistsException extends ResponseStatusException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public AlreadyExistsException(String message) {
        super(httpStatus, message);
        log.error(message);
    }

}
