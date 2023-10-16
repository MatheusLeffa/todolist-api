package br.com.matheushilbert.todolist.user.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.matheushilbert.todolist.exception.AlreadyExistsException;
import br.com.matheushilbert.todolist.user.model.UserModel;
import br.com.matheushilbert.todolist.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        // Verify if Login already exists on the DB.
        UserModel user = this.userRepository.findByLogin(userModel.getLogin());
        if(user != null){
            throw new AlreadyExistsException("O login "+ user.getLogin() + " já existe.");
        }

        // Hash the password
        String passwordHashed = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);

        // Create user on DB
        UserModel userCreated = this.userRepository.save(userModel);
        return ResponseEntity.ok().body( userCreated);
    }
}
