package br.com.matheushilbert.todolist.modules.user.mapper;

import br.com.matheushilbert.todolist.modules.user.dto.UserDTO;
import br.com.matheushilbert.todolist.modules.user.model.UserModel;
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
