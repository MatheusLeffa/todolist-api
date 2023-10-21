package br.com.matheushilbert.todolist.mapper;

import br.com.matheushilbert.todolist.dto.UserDTO;
import br.com.matheushilbert.todolist.model.UserModel;
import org.modelmapper.ModelMapper;

public class UserMapper {
    public static UserDTO entityToDto(UserModel userModel) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userModel, UserDTO.class);
    }

    public static UserModel dtoToEntity(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, UserModel.class);
    }
}
