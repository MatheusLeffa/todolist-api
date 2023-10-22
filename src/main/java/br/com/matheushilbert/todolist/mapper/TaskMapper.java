package br.com.matheushilbert.todolist.mapper;

import br.com.matheushilbert.todolist.dto.TaskDTO;
import br.com.matheushilbert.todolist.model.TaskModel;
import org.modelmapper.ModelMapper;

public class TaskMapper {

    public static TaskDTO entityToDto(TaskModel taskModel) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(taskModel, TaskDTO.class);
    }

    public static TaskModel dtoToEntity(TaskDTO taskDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(taskDTO, TaskModel.class);
    }

}
