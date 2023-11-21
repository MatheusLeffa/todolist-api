package br.com.matheushilbert.todolist.user.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.matheushilbert.todolist.exception.AlreadyExistsException;
import br.com.matheushilbert.todolist.exception.NotFoundException;
import br.com.matheushilbert.todolist.user.model.UserModel;
import br.com.matheushilbert.todolist.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel create(UserModel userModel) {
        // Verify if Login already exists on the DB.
        UserModel user = this.userRepository.findByLogin(userModel.getLogin());
        if (user != null) {
            throw new AlreadyExistsException("The login " + user.getLogin() + " already exists.");
        }

        // Hash the password
        String passwordHashed = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);

        // Create user on DB
        return this.userRepository.save(userModel);
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

}
