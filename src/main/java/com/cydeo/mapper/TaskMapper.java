package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private  final  ModelMapper modelMapper;                //solid principle do not crete all in one class-- create methode whatever we need use

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Task convertToEntity(TaskDTO  dto) {

        return modelMapper.map(dto, Task.class);//entity class

    }

    public TaskDTO convertToDto(Task entity) {
        return modelMapper.map(entity, TaskDTO.class);
    }
}


