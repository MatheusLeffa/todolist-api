package br.com.matheushilbert.todolist.user.controller;

import br.com.matheushilbert.todolist.user.dto.UserDTO;
import br.com.matheushilbert.todolist.user.mapper.UserMapper;
import br.com.matheushilbert.todolist.user.model.UserModel;
import br.com.matheushilbert.todolist.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public UserDTO create(@RequestBody @Valid UserDTO user) {
        UserModel entity = UserMapper.dtoToEntity(user);
        return UserMapper.entityToDto(userService.create(entity));
    }

    @GetMapping("/")
    public List<UserDTO> findAll() {
        return userService.findAll()
                .stream()
                .map(UserMapper::entityToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable UUID id) {
        return UserMapper.entityToDto(userService.findById(id));
    }

}
