package com.cydeo.mapper;


import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {//mapper coming from third party

    private ModelMapper modelMapper;                //solid principle do not crete all in one class-- create methode whatever we need use

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Role convertToEntity(RoleDTO dto) {

        return modelMapper.map(dto, Role.class);//entity class

    }

    public RoleDTO convertToDto(Role entity) {
        return modelMapper.map(entity, RoleDTO.class);
    }
}