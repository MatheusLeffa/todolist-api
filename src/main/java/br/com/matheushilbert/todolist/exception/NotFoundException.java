package br.com.matheushilbert.todolist.exception;


import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@StandardException
@Slf4j
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
