package br.com.matheushilbert.todolist.user.mapper;

import br.com.matheushilbert.todolist.user.dto.UserDTO;
import br.com.matheushilbert.todolist.user.model.UserModel;
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
